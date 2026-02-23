package com.gtservices.hms.billing.dto;

import com.gtservices.hms.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data

public class BillingDto
{

    private Integer paymentId;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private PaymentStatus paymentStatus;

}
