package com.gtservices.hms.appointment.repository;

import com.gtservices.hms.appointment.entity.Appointment;
import com.gtservices.hms.enums.AppointmentStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    boolean existsByDoctorDoctorIdAndAppointmentDateAndAppointmentTime(
            Integer doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
                SELECT a FROM Appointment a
                WHERE a.doctor.doctorId = :doctorId
                AND a.appointmentDate = :date
                AND a.appointmentTime = :time
            """)
    Optional<Appointment> lockSlot(
            @Param("doctorId") Integer doctorId,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time
    );

    @Query("""
                SELECT a.appointmentTime
                FROM Appointment a
                WHERE a.doctor.doctorId = :doctorId
                AND a.appointmentDate = :date
            """)
    List<LocalTime> findBookedSlots(
            @Param("doctorId") Integer doctorId,
            @Param("date") LocalDate date
    );

    @EntityGraph(attributePaths = {"doctor", "patient"})
    Page<Appointment> findByAppointmentDate(
            LocalDate appointmentDate,
            Pageable pageable
    );

    @Query("""
                SELECT a FROM Appointment a
                JOIN FETCH a.doctor
                JOIN FETCH a.patient
                WHERE a.appointmentDate = :date
            """)
    Page<Appointment> findAllWithDetailsByDate(
            @Param("date") LocalDate date,
            Pageable pageable
    );

    @Query("SELECT a FROM Appointment a " +
            "JOIN FETCH a.doctor " +
            "WHERE a.patient.patientId = :patientId")
    List<Appointment> findAppointmentsWithDoctorByPatientId(@Param("patientId") Integer patientId);

    Page<Appointment> findByPatientPatientId(Integer patientId, Pageable pageable);

    Page<Appointment>findByPatientPatientIdAndAppointmentStatus(Integer patientId, AppointmentStatus appointmentStatus, Pageable pageable);

    List<Appointment> findByDoctor_DoctorIdAndAppointmentDate(
            Integer doctorId,
            LocalDate appointmentDate
    );

    Page<Appointment> findByDoctorDoctorId(Integer doctorId, Pageable pageable);

    Page<Appointment> findByDoctorDoctorIdAndAppointmentDate(
            Integer doctorId,
            LocalDate appointmentDate,
            Pageable pageable
    );

    Page<Appointment> findByDoctorDoctorIdAndAppointmentStatus(
            Integer doctorId,
            AppointmentStatus status,
            Pageable pageable
    );

    Page<Appointment> findByDoctorDoctorIdAndAppointmentDateAndAppointmentStatus(
            Integer doctorId,
            LocalDate appointmentDate,
            AppointmentStatus status,
            Pageable pageable
    );
}
