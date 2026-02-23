package com.gtservices.hms.report.entity;

import com.gtservices.hms.appointment.entity.Appointment;
import com.gtservices.hms.billing.entity.OpdBill;
import com.gtservices.hms.consultation.entity.Consultation;
import com.gtservices.hms.consultation.entity.Prescription;
import com.gtservices.hms.doctor.entity.Doctor;
import com.gtservices.hms.ipd.entity.IpdAdmission;
import com.gtservices.hms.ipd.entity.IpdInvoice;
import com.gtservices.hms.patient.entity.Patient;
import com.gtservices.hms.patient.entity.PatientVisit;
import com.gtservices.hms.user.entity.User;
import com.gtservices.hms.enums.ReportType;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "patient_reports",
        uniqueConstraints = @UniqueConstraint(columnNames = "report_uid")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Integer reportId;

    @Column(name = "report_uid", nullable = false, length = 25, unique = true)
    private String reportUid;

    // ===== FOREIGN KEYS =====

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id")
    private PatientVisit visit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opd_bill_id")
    private OpdBill opdBill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ipd_admission_id")
    private IpdAdmission ipdAdmission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ipd_invoice_id")
    private IpdInvoice ipdInvoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_by")
    private User generatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    @Column(name = "report_title", length = 150)
    private String reportTitle;

    @Column(name = "report_description", columnDefinition = "TEXT")
    private String reportDescription;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;
}