package com.gtservices.hms.appointment.entity;

import com.gtservices.hms.consultation.entity.Consultation;
import com.gtservices.hms.doctor.entity.Doctor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "follow_up_visits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowUpVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "followup_id")
    private Integer followupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followup_doctor_id")
    private Doctor followupDoctor;

    @Column(name = "followup_date")
    private LocalDate followupDate;

    @Column(name = "followup_time")
    private LocalTime followupTime;
}