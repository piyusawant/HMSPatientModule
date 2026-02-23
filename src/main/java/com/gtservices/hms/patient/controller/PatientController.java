package com.gtservices.hms.patient.controller;

import com.gtservices.hms.appointment.dto.AppointmentResponseDto;
import com.gtservices.hms.appointment.dto.PatientAppointmentsDto;
import com.gtservices.hms.billing.dto.BillingDto;
import com.gtservices.hms.enums.AppointmentStatus;
import com.gtservices.hms.patient.dto.FollowUpResponseDto;
import com.gtservices.hms.patient.dto.PatientHistoryResponseDto;
import com.gtservices.hms.patient.dto.PatientRequestDto;
import com.gtservices.hms.patient.dto.PatientResponseDto;
import com.gtservices.hms.patient.entity.Patient;
import com.gtservices.hms.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Page<PatientResponseDto> searchPatients(@RequestParam String query,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "0")int size)
    {
        return patientService.searchPatients(query, page, size);
    }

    //Get Patient by ID
    @GetMapping("/{patientId}")
    public Patient getPatientById(@PathVariable("patientId") Integer patientId)
    {
        return patientService.getPatientById(patientId);
    }

    //Get appointment with patient id and Status
    @GetMapping("/{patientId}/appointments")
    public ResponseEntity<Page<AppointmentResponseDto>>getAppointments(@PathVariable Integer patientId,
                                                                       @RequestParam(required = false)
                                                                       AppointmentStatus appointmentStatus,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "5") int size)
    {
        Page<AppointmentResponseDto> response = patientService.getPatientAppointments(patientId, appointmentStatus, page, size);

        return ResponseEntity.ok(response);
    }

    //Get patient billing details
    @GetMapping("/{patientId}/bill")
    public ResponseEntity<List<BillingDto>>getBilling(@PathVariable Integer patientId)
    {
        return ResponseEntity.ok(patientService.getPatientBilling(patientId));
    }

    //Get Patient FollowUp Visit
    @GetMapping("/{patientId}/follow-ups")
    public ResponseEntity<List<FollowUpResponseDto>>getFollowUps(@PathVariable Integer patientId)
    {
        return ResponseEntity.ok(
                patientService.getPatientFollowUps(patientId));
    }

    //Get Patient History
    @GetMapping("/{patientId}/history")
    public ResponseEntity<PatientHistoryResponseDto> getPatientHistory(@PathVariable Integer patientId)
    {
        return ResponseEntity.ok(patientService.getPatientHistory(patientId));
    }









}
