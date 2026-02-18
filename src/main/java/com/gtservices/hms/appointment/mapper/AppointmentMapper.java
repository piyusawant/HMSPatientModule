package com.gtservices.hms.appointment.mapper;

import com.gtservices.hms.appointment.dto.*;
import com.gtservices.hms.appointment.entity.Appointment;
import com.gtservices.hms.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentMapper {

    private AppointmentMapper(){}

    public static Appointment toEntity(AppointmentCreateRequestDto dto) {

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setReasonForVisit(dto.getReasonForVisit());
        appointment.setBookingSource(dto.getBookingSource());
        appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setAppointmentUid(generateUid());

        return appointment;
    }

    public static AppointmentResponseDto toDto(Appointment appointment) {

        return AppointmentResponseDto.builder()
                .appointmentId(appointment.getAppointmentId())
                .appointmentUid(appointment.getAppointmentUid())
                .patientName(appointment.getPatient().getPatientName())
                .doctorName(appointment.getDoctor().getDoctorName())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .reasonForVisit(appointment.getReasonForVisit())
                .bookingSource(appointment.getBookingSource().name())
                .appointmentStatus(appointment.getAppointmentStatus().name())
                .build();
    }

    public static AppointmentDto mapToDto(Appointment appointment)
    {
        if(appointment == null)
            return null;
        AppointmentDto dto = new AppointmentDto();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setAppointmentStatus(appointment.getAppointmentStatus());
        return dto;
    }

    private static String generateUid() {
        return "APT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}