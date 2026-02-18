package com.gtservices.hms.billing.repository;

import com.gtservices.hms.billing.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer>
{
    List<Payment> findByPatientPatientId(Integer patientId);
}
