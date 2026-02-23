package com.gtservices.hms.patient.dto;

import com.gtservices.hms.enums.ReportType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportSummaryDto
{
    private Integer reportId;
    private String reportUid;
    private ReportType reportType;
    private String reportTitle;
    private String reportDescription;
    private LocalDateTime generatedAt;
}
