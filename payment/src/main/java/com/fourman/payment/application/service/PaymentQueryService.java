package com.fourman.payment.application.service;


import com.fourman.common.dto.request.GetPaymentUrlRequest;

public interface PaymentQueryService {
    String getPaymentUrl(GetPaymentUrlRequest getPaymentUrlRequest, String clientIp);
}
