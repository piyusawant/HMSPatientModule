package com.gtservices.hms.patient.repository;

import com.gtservices.hms.patient.entity.Patient;
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

    @Query("SELECT p FROM Patient p WHERE " +
            "LOWER(p.patientName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "p.mobileNo LIKE CONCAT('%', :query, '%')")
    List<Patient> searchPatients(@Param("query") String query);
}
