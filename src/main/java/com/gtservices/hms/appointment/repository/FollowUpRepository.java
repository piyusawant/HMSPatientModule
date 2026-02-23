package com.gtservices.hms.appointment.repository;

import com.gtservices.hms.appointment.entity.FollowUpVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowUpRepository extends JpaRepository<FollowUpVisit, Integer>
{
    List<FollowUpVisit> findByConsultation_Visit_Patient_PatientId(Integer patientId);
}
