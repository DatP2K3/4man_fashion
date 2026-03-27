package com.fourman.payment.application.service;

import jakarta.servlet.http.HttpServletRequest;

import com.fourman.common.dto.request.GetPaymentUrlRequest;

public interface PaymentQueryService {
    String getPaymentUrl(GetPaymentUrlRequest getPaymentUrlRequest, HttpServletRequest request);
}
