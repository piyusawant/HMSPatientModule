package com.gtservices.hms.appointment.serviceImpl;

import com.gtservices.hms.appointment.dto.AppointmentCreateRequestDto;
import com.gtservices.hms.appointment.dto.AppointmentResponseDto;
import com.gtservices.hms.appointment.dto.AvailableSlotDto;
import com.gtservices.hms.appointment.entity.Appointment;
import com.gtservices.hms.appointment.mapper.AppointmentMapper;
import com.gtservices.hms.appointment.repository.AppointmentRepository;
import com.gtservices.hms.appointment.service.AppointmentService;
import com.gtservices.hms.common.exception.ResourceNotFoundException;
import com.gtservices.hms.common.exception.SlotAlreadyBookedException;
import com.gtservices.hms.common.response.PageResponseDto;
import com.gtservices.hms.doctor.entity.Doctor;
import com.gtservices.hms.doctor.entity.DoctorAvailability;
import com.gtservices.hms.doctor.repository.DoctorAvailabilityRepository;
import com.gtservices.hms.doctor.repository.DoctorRepository;
import com.gtservices.hms.patient.entity.Patient;
import com.gtservices.hms.patient.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorAvailabilityRepository doctorAvailabilityRepository;

    @Override
    public AppointmentResponseDto bookAppointment(AppointmentCreateRequestDto request) {



        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));


        validateDoctorAvailability(doctor, request.getAppointmentDate(), request.getAppointmentTime());

        Optional<Appointment> existing =
                appointmentRepository.lockSlot(
                        request.getDoctorId(),
                        request.getAppointmentDate(),
                        request.getAppointmentTime()
                );

        if (existing.isPresent()) {
            throw new SlotAlreadyBookedException("Slot already booked");
        }

        if (appointmentRepository.existsByDoctorDoctorIdAndAppointmentDateAndAppointmentTime(
                request.getDoctorId(),
                request.getAppointmentDate(),
                request.getAppointmentTime())) {

            throw new RuntimeException("Slot already booked");
        }

        Appointment appointment = AppointmentMapper.toEntity(request);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        try {
            Appointment saved = appointmentRepository.save(appointment);
            return AppointmentMapper.toDto(saved);

        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Slot already booked by another user");
        }
    }

    @Override
    public List<AvailableSlotDto> getAvailableSlots(Integer doctorId,
                                                    LocalDate date) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        DoctorAvailability.DayOfWeek day =
                DoctorAvailability.DayOfWeek.valueOf(date.getDayOfWeek().name());

        DoctorAvailability availability =
                doctorAvailabilityRepository
                        .findByDoctorAndDayOfWeek(doctor, day)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Doctor not available")
                        );

        List<LocalTime> bookedSlots =
                appointmentRepository.findBookedSlots(doctorId, date);

        List<AvailableSlotDto> slots = new ArrayList<>();

        LocalTime start = availability.getStartTime();
        LocalTime end = availability.getEndTime();

        while (start.isBefore(end)) {

            boolean isBooked = bookedSlots.contains(start);

            slots.add(new AvailableSlotDto(start, !isBooked));

            start = start.plusMinutes(30);
        }

        return slots;
    }

    @Override
    public PageResponseDto<AppointmentResponseDto> getAppointmentsByDate(
            LocalDate date,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Appointment> appointmentPage =
                appointmentRepository.findByAppointmentDate(date, pageable);

        List<AppointmentResponseDto> content =
                appointmentPage.getContent()
                        .stream()
                        .map(AppointmentMapper::toDto)
                        .toList();

        return PageResponseDto.<AppointmentResponseDto>builder()
                .content(content)
                .pageNumber(appointmentPage.getNumber())
                .pageSize(appointmentPage.getSize())
                .totalElements(appointmentPage.getTotalElements())
                .totalPages(appointmentPage.getTotalPages())
                .last(appointmentPage.isLast())
                .build();
    }

    private void validateDoctorAvailability(Doctor doctor,
                                            LocalDate date,
                                            LocalTime time) {

        if (Boolean.FALSE.equals(doctor.getIsActive())) {
            throw new RuntimeException("Doctor not active");
        }

        DoctorAvailability.DayOfWeek day =
                DoctorAvailability.DayOfWeek.valueOf(date.getDayOfWeek().name());

        DoctorAvailability availability =doctorAvailabilityRepository
                .findByDoctorAndDayOfWeek(doctor, day)
                        .orElseThrow(() -> new RuntimeException("Doctor not available on this day"));

        if (time.isBefore(availability.getStartTime()) ||
                time.isAfter(availability.getEndTime())) {

            throw new RuntimeException("Selected time outside working hours");
        }
    }
}
