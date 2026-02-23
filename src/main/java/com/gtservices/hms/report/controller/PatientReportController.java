package com.gtservices.hms.report.controller;

import com.gtservices.hms.report.dto.PatientReportResponseDto;
import com.gtservices.hms.report.service.PatientReportService;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients/reports")
@RequiredArgsConstructor
public class PatientReportController {

    private final PatientReportService reportService;

    @GetMapping("/{patientId}")
    public ResponseEntity<List<PatientReportResponseDto>> getPatientReports(@PathVariable Integer patientId) {

        return ResponseEntity.ok(
                reportService.getReportsByPatientId(patientId));

    }

    @GetMapping("/{reportId}/download-pdf")
    public ResponseEntity<byte[]> downloadPdf(
            @PathVariable Integer reportId) {

        byte[] pdfData = reportService.generateAndDownloadPdf(reportId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=medical-report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);


    }
}
