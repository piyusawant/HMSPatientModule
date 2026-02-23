package com.gtservices.hms.report.mapper;

import com.gtservices.hms.enums.DocumentType;
import com.gtservices.hms.report.dto.PatientReportResponseDto;
import com.gtservices.hms.report.entity.DocumentStore;
import com.gtservices.hms.report.entity.PatientReport;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PatientReportMapper
{
    public PatientReportResponseDto toDto(PatientReport report) {

        PatientReportResponseDto dto = new PatientReportResponseDto();

        dto.setReportUid(report.getReportUid());
        dto.setReportType(report.getReportType());
        dto.setReportTitle(report.getReportTitle());
        dto.setReportDescription(report.getReportDescription());
        dto.setGeneratedAt(report.getGeneratedAt());

        dto.setPatientId(report.getPatient().getPatientId());
        dto.setPatientName(report.getPatient().getPatientName());
        dto.setPatientEmail(report.getPatient().getEmail());

        dto.setDoctorId(report.getConsultation().getDoctor().getDoctorId());
        dto.setDoctorName(report.getConsultation().getDoctor().getDoctorName());
        dto.setSpecializationName(String.valueOf(report.getConsultation().getDoctor().getSpecialization()));

        return dto;
    }

    public DocumentStore mapToDocumentStore(Integer reportId, String filePath)
    {
        DocumentStore documentStore = new DocumentStore();

        documentStore.setDocumentType(DocumentType.LAB_REPORT);
        documentStore.setReferenceId(reportId);
        documentStore.setFilePath(filePath);

        documentStore.setGeneratedAt(LocalDateTime.now());

        return documentStore;

    }



}
