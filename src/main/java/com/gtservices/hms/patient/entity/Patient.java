package com.gtservices.hms.patient.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gtservices.hms.appointment.entity.Appointment;
import com.gtservices.hms.billing.entity.Payment;
import com.gtservices.hms.user.entity.User;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "patient_uid", unique = true, length = 20)
    private String patientUid;

    @Column(name = "patient_name", length = 100)
    private String patientName;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "mobile_no", unique = true, length = 15)
    private String mobileNo;

    @Column(name = "age")
    private Integer age;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "blood_group", length = 10)
    private String bloodGroup;

    @Column(name = "blood_pressure", length = 20)
    private String bloodPressure;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PatientVisit> visits;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<Payment> payments;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PatientFamily> familyMembers;


}