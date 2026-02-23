package com.gtservices.hms.report.repository;

import com.gtservices.hms.report.entity.PatientReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientReportRepository extends JpaRepository<PatientReport, Integer>
{
    List<PatientReport>findByPatientPatientId(Integer patientId);

     List<PatientReport>findByPatient_PatientIdOrderByGeneratedAtDesc(Integer patientId);


}
