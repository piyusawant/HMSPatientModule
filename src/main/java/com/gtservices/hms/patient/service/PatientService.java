package com.gtservices.hms.patient.service;

import com.gtservices.hms.appointment.dto.AppointmentDto;
import com.gtservices.hms.appointment.dto.PatientAppointmentsDto;
import com.gtservices.hms.billing.dto.BillingDto;
import com.gtservices.hms.enums.AppointmentStatus;
import com.gtservices.hms.patient.dto.PatientRequestDto;
import com.gtservices.hms.patient.dto.PatientResponseDto;
import com.gtservices.hms.patient.entity.Patient;

import java.util.List;

public interface PatientService
{
    //Register Patient
    PatientResponseDto registerPatient(PatientRequestDto dto);
    //Get Appointment with only Patient ID
    PatientAppointmentsDto getPatientAppointments(Integer patientId);

    //Search Patient with Name, Email, MobileNo
    List<Patient> searchPatients(String query);
    //Get Patient with Patient ID
    Patient getPatientById(Integer patientId);

    //Get Appointment With Patient and Appointment Status
    List<AppointmentDto> getPatientAppointments(Integer patientId, AppointmentStatus appointmentStatus);
    //Get Patient Billing details
    List<BillingDto> getPatientBilling(Integer patientId);


}
