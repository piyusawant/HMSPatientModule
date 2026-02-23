package com.gtservices.hms.patient.mapper;

import com.gtservices.hms.appointment.entity.FollowUpVisit;
import com.gtservices.hms.patient.dto.FollowUpResponseDto;

public class PatientFollowUpMapper
{
    public static FollowUpResponseDto toDto(FollowUpVisit followUp) {

        return FollowUpResponseDto.builder()
                .followupId(followUp.getFollowupId())
                .patientName(
                        followUp.getConsultation()
                                .getVisit()
                                .getPatient()
                                .getPatientName()
                )
                .doctorName(
                        followUp.getFollowupDoctor() != null
                                ? followUp.getFollowupDoctor().getDoctorName()
                                : null
                )
                .followupDate(followUp.getFollowupDate())
                .followupTime(followUp.getFollowupTime())
                .doctorNotes(followUp.getConsultation().getDoctorNotes())
                .build();
    }
}
