package com.gtservices.hms.report.serviceImpl;

import com.gtservices.hms.report.dto.PatientReportResponseDto;
import com.gtservices.hms.report.entity.DocumentStore;
import com.gtservices.hms.report.entity.PatientReport;
import com.gtservices.hms.report.mapper.PatientReportMapper;
import com.gtservices.hms.report.repository.DocumentStoreRepository;
import com.gtservices.hms.report.repository.PatientReportRepository;
import com.gtservices.hms.report.service.PatientReportService;
import com.gtservices.hms.report.util.PdfGeneratorUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements PatientReportService
{
    @Value("${file.upload-dir:uploads/reports}")
    private String uploadDir;

    private final PatientReportRepository reportRepository;
    private final PatientReportMapper mapper;
    private final DocumentStoreRepository documentStoreRepository;

    @Override
    public List<PatientReportResponseDto>
    getReportsByPatientId(Integer patientId)
    {

        return reportRepository.findByPatientPatientId(patientId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public byte[] generateAndDownloadPdf(Integer reportId)
    {
        PatientReport report = reportRepository.findById(reportId).orElseThrow(() ->
                new RuntimeException("Report Not Found"));

        byte[] pdfBytes = PdfGeneratorUtil.generateReport(report);

        try
        {
            Path uploadPath = Paths.get(uploadDir)
                    .toAbsolutePath()
                    .normalize();

            Files.createDirectories(uploadPath);

            String fileName = "REPORT_" +
                    report.getReportUid() + ".pdf";

            Path filePath = uploadPath.resolve(fileName);

            Files.write(filePath, pdfBytes);

            DocumentStore documentStore = mapper.mapToDocumentStore(reportId, filePath.toString());

            documentStoreRepository.save(documentStore);

            return pdfBytes;

        }catch (Exception e)
        {
            throw new RuntimeException("PDF Generation Failed", e);
        }
    }


}