package com.gtservices.hms.patient.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

@Entity
@Table(name = "patient_family")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientFamily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "family_id")
    private Integer familyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;

    @Column(name = "member_name", length = 100)
    private String memberName;

    @Column(name = "relation", length = 50)
    private String relation;

    @Column(name = "contact_no", length = 15)
    private String contactNo;


}