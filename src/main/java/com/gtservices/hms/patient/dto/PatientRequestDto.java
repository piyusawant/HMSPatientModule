package com.gtservices.hms.patient.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PatientRequestDto {

    private String patientName;
    private String address;
    private String mobileNo;
    private LocalDate dateOfBirth;
    private String email;
    private String bloodGroup;
    private String bloodPressure;
    private Double weight;
    private List<PatientFamilyDto> familyMembers;

}
