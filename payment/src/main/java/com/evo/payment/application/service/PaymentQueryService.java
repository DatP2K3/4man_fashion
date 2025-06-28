package com.evo.payment.application.service;

import jakarta.servlet.http.HttpServletRequest;

import com.evo.common.dto.request.GetPaymentUrlRequest;

public interface PaymentQueryService {
    String getPaymentUrl(GetPaymentUrlRequest getPaymentUrlRequest, HttpServletRequest request);
}
