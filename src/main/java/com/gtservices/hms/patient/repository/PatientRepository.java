package com.gtservices.hms.patient.repository;

import com.gtservices.hms.patient.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>
{

    Optional<Patient> findByMobileNo(String mobileNo);
    Optional<Patient>findByPatientId(Integer patientId);
    Page<Patient>findByPatientNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileNoContaining(
            String patientName, String email, String mobileNo, Pageable pageable);
}
