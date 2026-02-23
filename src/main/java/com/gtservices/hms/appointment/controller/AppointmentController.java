package com.gtservices.hms.appointment.controller;

import com.gtservices.hms.appointment.dto.AppointmentCreateRequestDto;
import com.gtservices.hms.appointment.dto.AppointmentResponseDto;
import com.gtservices.hms.appointment.dto.AvailableSlotDto;
import com.gtservices.hms.appointment.service.AppointmentService;
import com.gtservices.hms.common.response.PageResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<AppointmentResponseDto> bookAppointment(
            @Valid @RequestBody AppointmentCreateRequestDto request) {

        return ResponseEntity.ok(
                appointmentService.bookAppointment(request)
        );
    }

    @GetMapping("/slots")
    public ResponseEntity<List<AvailableSlotDto>> getSlots(
            @RequestParam Integer doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(
                appointmentService.getAvailableSlots(doctorId, date)
        );
    }

    @GetMapping("/by-date")
    public ResponseEntity<PageResponseDto<AppointmentResponseDto>> getAppointmentsByDate(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "appointmentTime")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ) {

        return ResponseEntity.ok(
                appointmentService.getAppointmentsByDate(
                        date, page, size, sortBy, direction
                )
        );
    }


}
