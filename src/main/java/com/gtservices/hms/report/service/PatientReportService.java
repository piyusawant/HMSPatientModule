package com.gtservices.hms.report.service;

import com.gtservices.hms.report.dto.PatientReportResponseDto;
import com.gtservices.hms.report.entity.PatientReport;

import java.util.List;

public interface PatientReportService
{
    List<PatientReportResponseDto>getReportsByPatientId(Integer patientId);


    byte[] generateAndDownloadPdf(Integer reportId);
}
