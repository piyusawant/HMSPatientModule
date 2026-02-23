package com.gtservices.hms.doctor.service;

import com.gtservices.hms.doctor.dto.DoctorRegistrationRequestDto;
import com.gtservices.hms.doctor.dto.DoctorResponseDto;

public interface DoctorService {

    DoctorResponseDto registerDoctor(DoctorRegistrationRequestDto request);
}

