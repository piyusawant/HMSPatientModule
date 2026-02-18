package com.gtservices.hms.patient.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PatientDto
{
    private Integer patientId;
    private String patientName;
    private String email;
    private String mobileNo;

}