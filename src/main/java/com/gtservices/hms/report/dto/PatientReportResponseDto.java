package com.gtservices.hms.report.dto;

import com.gtservices.hms.enums.ReportType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientReportResponseDto
{
    private Integer reportId;
    private String reportUid;
    private ReportType reportType;
    private String reportTitle;
    private String reportDescription;
    private LocalDateTime generatedAt;

    private Integer patientId;
    private String patientName;
    private String patientEmail;
    private String mobileNo;

    private Integer doctorId;
    private String doctorName;
    private String specializationName;

}
