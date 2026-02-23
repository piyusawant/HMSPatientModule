package com.gtservices.hms.patient.mapper;

import com.gtservices.hms.appointment.dto.AppointmentResponseDto;
import com.gtservices.hms.appointment.dto.PatientAppointmentsDto;
import com.gtservices.hms.appointment.entity.Appointment;
import com.gtservices.hms.patient.dto.PatientFamilyDto;
import com.gtservices.hms.patient.dto.PatientResponseDto;
import com.gtservices.hms.patient.dto.ReportSummaryDto;
import com.gtservices.hms.patient.entity.Patient;
import com.gtservices.hms.report.entity.PatientReport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientMapper
{
    public static PatientResponseDto mapToDTO(Patient patient) {

        PatientResponseDto dto = new PatientResponseDto();
        dto.setPatientId(patient.getPatientId());
        dto.setPatientName(patient.getPatientName());
        dto.setMobileNo(patient.getMobileNo());
        dto.setEmail(patient.getEmail());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setAge(patient.getAge());
        dto.setAddress(patient.getAddress());
        dto.setBloodGroup(patient.getBloodGroup());
        dto.setBloodPressure(patient.getBloodPressure());
        dto.setWeight(patient.getWeight());

        if (patient.getFamilyMembers() != null) {
            List<PatientFamilyDto> familyDtos = patient.getFamilyMembers().stream().map(f -> {
                PatientFamilyDto fdto = new PatientFamilyDto();
                fdto.setMemberName(f.getMemberName());
                fdto.setRelation(f.getRelation());
                fdto.setContactNo(f.getContactNo());
                return fdto;
            }).collect(Collectors.toList());
            dto.setFamilyMembers(familyDtos);
        }
        return dto;
    }

    private static AppointmentResponseDto toAppointmentDto(Appointment a)
    {
        AppointmentResponseDto dto = new AppointmentResponseDto();

        dto.setAppointmentId(a.getAppointmentId());
        dto.setAppointmentUid(a.getAppointmentUid());
        dto.setPatientName(a.getPatient().getPatientName());
        dto.setDoctorName(a.getDoctor().getDoctorName());
        dto.setAppointmentDate(a.getAppointmentDate());
        dto.setAppointmentTime(a.getAppointmentTime());
        dto.setReasonForVisit(a.getReasonForVisit());
        dto.setBookingSource(String.valueOf(a.getBookingSource()));
        dto.setAppointmentStatus(String.valueOf(a.getAppointmentStatus()));
        return dto;
    }

    public static PatientAppointmentsDto toPatientAppointmentDto(Patient patient, List<Appointment> appointments) {
        List<AppointmentResponseDto> appointmentResponseDtoList = appointments.stream()
                .map(PatientMapper::toAppointmentDto)
                .collect(Collectors.toList());

        PatientAppointmentsDto dto = new PatientAppointmentsDto();
        dto.setPatientId(patient.getPatientId());
        dto.setPatientName(patient.getPatientName());
        dto.setEmail(patient.getEmail());
        dto.setMobileNo(patient.getMobileNo());
        dto.setAppointments(appointmentResponseDtoList);
        return dto;
    }

    public static AppointmentResponseDto toAppointmentResponseDto(Appointment appointment)
    {
        AppointmentResponseDto dto = new AppointmentResponseDto();

        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setAppointmentUid(appointment.getAppointmentUid());
        dto.setPatientName(appointment.getPatient().getPatientName());
        dto.setDoctorName(appointment.getDoctor().getDoctorName());
        dto.setSpecializationName(appointment.getDoctor().getSpecialization().getSpecializationName());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setReasonForVisit(appointment.getReasonForVisit());
        dto.setAppointmentStatus(String.valueOf(appointment.getAppointmentStatus()));

        return dto;
    }
    static public ReportSummaryDto toReportSummaryDto(PatientReport report)
    {
        return ReportSummaryDto.builder()
                .reportId(report.getReportId())
                .reportUid(report.getReportUid())
                .reportType(report.getReportType())
                .reportTitle(report.getReportTitle())
                .reportDescription(report.getReportDescription())
                .generatedAt(report.getGeneratedAt())
                .build();
    }
}
