package com.gtservices.hms.appointment.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto {

    private Integer appointmentId;
    private String appointmentUid;

    private String patientName;
    private String doctorName;
    private String specializationName;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    private String reasonForVisit;

    private String bookingSource;
    private String appointmentStatus;
}
