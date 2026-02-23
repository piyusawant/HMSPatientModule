package com.gtservices.hms.patient.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FollowUpResponseDto
{
    private Integer followupId;
    private String patientName;
    private String doctorName;
    private LocalDate followupDate;
    private LocalTime followupTime;
    private String doctorNotes;


}
