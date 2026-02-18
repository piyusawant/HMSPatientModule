package com.gtservices.hms.billing.mapper;

import com.gtservices.hms.billing.dto.BillingDto;
import com.gtservices.hms.billing.entity.Payment;

public class BillingMapper
{
    private BillingMapper()
    { }

    public static BillingDto mapToDto(Payment payment)
    {
        if(payment == null)
            return null;

        BillingDto dto = new BillingDto();
        dto.setPaymentId(payment.getPaymentId());
        dto.setAmount(payment.getAmount());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setPaymentStatus(payment.getPaymentStatus());
        return dto;
    }
}
