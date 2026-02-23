package com.gtservices.hms.doctor.controller;

import com.gtservices.hms.doctor.dto.DoctorRegistrationRequestDto;
import com.gtservices.hms.doctor.dto.DoctorResponseDto;
import com.gtservices.hms.doctor.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<DoctorResponseDto> registerDoctor(
            @RequestBody DoctorRegistrationRequestDto request) {

        DoctorResponseDto response = doctorService.registerDoctor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

