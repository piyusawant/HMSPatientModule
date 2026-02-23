package com.gtservices.hms.patient.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientHistoryResponseDto
{
    private Integer patientId;
    private String patientName;
    private List<ReportSummaryDto> reports;
}
