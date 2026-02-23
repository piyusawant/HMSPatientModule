package com.gtservices.hms.appointment.dto;

import com.gtservices.hms.enums.AppointmentStatus;
import com.gtservices.hms.patient.entity.Patient;
import lombok.*;


import java.time.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientAppointmentsDto
{

    private Integer patientId;
    private String patientName;
    private String email;
    private String mobileNo;
    private List<AppointmentResponseDto> appointments;

}
