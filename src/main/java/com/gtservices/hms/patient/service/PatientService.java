package com.gtservices.hms.patient.service;

import com.gtservices.hms.appointment.dto.AppointmentResponseDto;
import com.gtservices.hms.appointment.dto.PatientAppointmentsDto;
import com.gtservices.hms.billing.dto.BillingDto;
import com.gtservices.hms.enums.AppointmentStatus;
import com.gtservices.hms.patient.dto.FollowUpResponseDto;
import com.gtservices.hms.patient.dto.PatientHistoryResponseDto;
import com.gtservices.hms.patient.dto.PatientRequestDto;
import com.gtservices.hms.patient.dto.PatientResponseDto;
import com.gtservices.hms.patient.entity.Patient;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientService
{
    //Register Patient
    PatientResponseDto registerPatient(PatientRequestDto dto);
    //Get Appointment with only Patient ID
    PatientAppointmentsDto getPatientAppointments(Integer patientId);

    //Search Patient with Name, Email, MobileNo
    Page<PatientResponseDto> searchPatients(String query, int page, int size);
    //Get Patient with Patient ID
    Patient getPatientById(Integer patientId);

    //Get Appointment With Patient and Appointment Status
    Page<AppointmentResponseDto> getPatientAppointments(Integer patientId, AppointmentStatus appointmentStatus, int page, int size);
    //Get Patient Billing details
    List<BillingDto> getPatientBilling(Integer patientId);

    //Get Patient return Visit by own
    List<FollowUpResponseDto>getPatientFollowUps(Integer patientId);

    //Get Patient report Information with Patient ID
    PatientHistoryResponseDto getPatientHistory(Integer patientId);


}
