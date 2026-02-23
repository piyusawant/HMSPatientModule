package com.gtservices.hms.patient.serviceImpl;

import com.gtservices.hms.appointment.dto.AppointmentResponseDto;
import com.gtservices.hms.appointment.dto.PatientAppointmentsDto;
import com.gtservices.hms.appointment.entity.Appointment;
import com.gtservices.hms.appointment.entity.FollowUpVisit;
import com.gtservices.hms.appointment.repository.AppointmentRepository;
import com.gtservices.hms.appointment.repository.FollowUpRepository;
import com.gtservices.hms.billing.dto.BillingDto;
import com.gtservices.hms.billing.entity.Payment;
import com.gtservices.hms.billing.mapper.BillingMapper;
import com.gtservices.hms.billing.repository.PaymentRepository;
import com.gtservices.hms.enums.AppointmentStatus;
import com.gtservices.hms.patient.dto.*;
import com.gtservices.hms.patient.entity.Patient;
import com.gtservices.hms.patient.entity.PatientFamily;
import com.gtservices.hms.patient.mapper.PatientFollowUpMapper;
import com.gtservices.hms.patient.mapper.PatientMapper;
import com.gtservices.hms.patient.repository.PatientRepository;
import com.gtservices.hms.patient.service.PatientService;
import com.gtservices.hms.patient.utility.UIDGenerator;
import com.gtservices.hms.report.entity.PatientReport;
import com.gtservices.hms.report.repository.PatientReportRepository;
import com.gtservices.hms.user.entity.Role;
import com.gtservices.hms.user.entity.User;
import com.gtservices.hms.user.repository.RoleRepository;
import com.gtservices.hms.user.repository.UserRepository;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService
{
    private final PatientRepository patientRepository;
    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;
    private final FollowUpRepository followUpRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PatientReportRepository patientReportRepository;

    //Patient Registration
    @Override
    public PatientResponseDto registerPatient(PatientRequestDto dto)
    {
        if(patientRepository.findByMobileNo(dto.getMobileNo()).isPresent())
        {
            throw new RuntimeException("Patient Already Exist");
        }
        //Fetch Role
        Role patientRole = roleRepository.findByRoleName("PATIENT")
                .orElseThrow(() -> new RuntimeException("PATIENT role not found"));

        //Create User
        User user = new User();
        user.setFullName(dto.getPatientName());
        user.setMobileNo(dto.getMobileNo());
        user.setEmail(dto.getEmail());

        // Manual password encoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPasswordHash(encoder.encode("12345"));

        user.setRole(patientRole);
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        //Create Patient
        Patient patient = new Patient();
        patient.setPatientUid(UIDGenerator.generatePatientUID());
        patient.setPatientName(dto.getPatientName());
        patient.setMobileNo(dto.getMobileNo());
        patient.setEmail(dto.getEmail());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setAddress(dto.getAddress());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setBloodPressure(dto.getBloodPressure());
        patient.setWeight(dto.getWeight());
        patient.setUser(savedUser);
        patient.setCreatedAt(LocalDateTime.now());

        Patient savedPatient = patientRepository.save(patient);

        // Save family members
        if(dto.getFamilyMembers() != null && !dto.getFamilyMembers().isEmpty()) {
            List<PatientFamily> familyList = dto.getFamilyMembers().stream().map(f -> {
                PatientFamily family = new PatientFamily();
                family.setMemberName(f.getMemberName());
                family.setRelation(f.getRelation());
                family.setContactNo(f.getContactNo());
                family.setPatient(savedPatient);
                return family;
            }).collect(Collectors.toList());

            savedPatient.setFamilyMembers(familyList);
            patientRepository.save(savedPatient);
        }
        return PatientMapper.mapToDTO(savedPatient);
    }

    //Get Appointment with patient ID
    @Override
    public PatientAppointmentsDto getPatientAppointments(Integer patientId) {

        if(patientId == null)
        {
            throw new IllegalArgumentException("Patient ID must not be  null");
        }
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()-> new RuntimeException("Patient not found with ID: " + patientId));

       List<Appointment> appointments = appointmentRepository.findAppointmentsWithDoctorByPatientId(patientId);
        return PatientMapper.toPatientAppointmentDto(patient, appointments);
    }

    //Search Patient with name email mobile number
    @Override
    public Page<PatientResponseDto> searchPatients(String query, int page, int size)
    {
        PageRequest pageable = PageRequest.of(page, size);

        Page<Patient> patients = patientRepository.
                findByPatientNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileNoContaining(query, query, query,
                        pageable);
        return patients.map(PatientMapper::mapToDTO);
    }
    //Get Patient with id
    @Override
    public Patient getPatientById(Integer patientId)
    {
        return patientRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not Found with ID:" + patientId));
    }

    //Get Appointment with patient and  appointment status
    @Override
    public Page<AppointmentResponseDto>getPatientAppointments(Integer patientId, AppointmentStatus appointmentStatus, int page, int size)
    {
        patientRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Patient Not Found"));

        Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDate").descending());
        Page<Appointment> appointments;

        if(appointmentStatus != null)
        {
            appointments =appointmentRepository.findByPatientPatientIdAndAppointmentStatus(patientId,appointmentStatus,pageable);
        }else {
            appointments = appointmentRepository.findByPatientPatientId(patientId,pageable);
        }

        return appointments
                .map(PatientMapper::toAppointmentResponseDto);
    }

    //Get Billing Details of Patient
    @Override
    public List<BillingDto> getPatientBilling(Integer patientId)
    {
        patientRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Patient Not found"));

        List<Payment> payments = paymentRepository.findByPatientPatientId(patientId);

        return payments.stream()
                .map(BillingMapper::mapToDto).toList();

    }

    //Get Patient Return Visit FollowUP by own
    @Override
    public List<FollowUpResponseDto> getPatientFollowUps(Integer patientId)
    {
        List<FollowUpVisit> followUpVisits = followUpRepository.findByConsultation_Visit_Patient_PatientId(patientId);

        return followUpVisits.stream()
                .map(PatientFollowUpMapper::toDto)
                .collect(Collectors.toList());
    }

    //Get Patient report History
    @Override
    public PatientHistoryResponseDto getPatientHistory(Integer patientId)
    {
        Patient patient =patientRepository.findById(patientId).orElseThrow(() ->
                new RuntimeException("Patient Not Found"));

        List<PatientReport> reports = patientReportRepository.findByPatient_PatientIdOrderByGeneratedAtDesc(patientId);

        List<ReportSummaryDto> reportDtos = reports.stream()
                .map(PatientMapper::toReportSummaryDto)
                .toList();

        return PatientHistoryResponseDto.builder()
                .patientId(patient.getPatientId())
                .patientName(patient.getPatientName())
                .reports(reportDtos)
                .build();
    }





}
