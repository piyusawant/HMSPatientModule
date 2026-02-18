package com.gtservices.hms.patient.controller;

import com.gtservices.hms.appointment.dto.AppointmentDto;
import com.gtservices.hms.appointment.dto.PatientAppointmentsDto;
import com.gtservices.hms.billing.dto.BillingDto;
import com.gtservices.hms.enums.AppointmentStatus;
import com.gtservices.hms.patient.dto.PatientRequestDto;
import com.gtservices.hms.patient.dto.PatientResponseDto;
import com.gtservices.hms.patient.entity.Patient;
import com.gtservices.hms.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController
{
    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponseDto> registerPatient(@RequestBody PatientRequestDto dto)
    {
        return ResponseEntity.ok(patientService.registerPatient(dto));
    }

    @GetMapping("/appointments/{patientId}")
    public ResponseEntity<PatientAppointmentsDto> getPatientAppointments(@PathVariable Integer patientId)
    {
        PatientAppointmentsDto dto = patientService.getPatientAppointments(patientId);
        if(dto == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
    // Search patients
    @GetMapping("/search")
    public List<Patient> searchPatients(@RequestParam("query") String query)
    {
        return patientService.searchPatients(query);
    }

    //Get Patient by ID
    @GetMapping("/{patientId}")
    public Patient getPatientById(@PathVariable("patientId") Integer patientId)
    {
        return patientService.getPatientById(patientId);
    }

    //Get appointment with patient id and Status
    @GetMapping("/{patientId}/appointments")
    public ResponseEntity<List<AppointmentDto>>getAppointments(@PathVariable Integer patientId,
                                                               @RequestParam(required = false)AppointmentStatus appointmentStatus)
    {
        return ResponseEntity.ok(patientService.getPatientAppointments(patientId, appointmentStatus));
    }

    //Get patient billing de
    @GetMapping("/{patientId}/bill")
    public ResponseEntity<List<BillingDto>>getBilling(@PathVariable Integer patientId)
    {
        return ResponseEntity.ok(patientService.getPatientBilling(patientId));
    }






















}
