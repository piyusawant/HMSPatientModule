/*package com.gtservices.hms.appointment.service;

import com.gtservices.hms.appointment.dto.AppointmentCreateRequestDto;
import com.gtservices.hms.appointment.dto.AppointmentResponseDto;
import com.gtservices.hms.appointment.dto.AvailableSlotDto;
import com.gtservices.hms.common.response.PageResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    AppointmentResponseDto bookAppointment(AppointmentCreateRequestDto request);
    List<AvailableSlotDto> getAvailableSlots(Integer doctorId, LocalDate date);
    PageResponseDto<AppointmentResponseDto> getAppointmentsByDate( LocalDate date,
                                                                   int page,
                                                                   int size,
                                                                   String sortBy,
                                                                   String direction);

}
*/