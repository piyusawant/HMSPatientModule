package com.gtservices.hms.appointment.repository;

import com.gtservices.hms.appointment.entity.Appointment;
import com.gtservices.hms.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>
{
    @Query("SELECT a FROM Appointment a " +
            "JOIN FETCH a.doctor " +
            "WHERE a.patient.patientId = :patientId")
    List<Appointment> findAppointmentsWithDoctorByPatientId(@Param("patientId") Integer patientId);

    List<Appointment>findByPatientPatientId(Integer patientId);

    List<Appointment>findByPatientPatientIdAndAppointmentStatus(Integer patientId, AppointmentStatus appointmentStatus);

}
