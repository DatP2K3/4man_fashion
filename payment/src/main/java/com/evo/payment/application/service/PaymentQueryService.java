package com.evo.payment.application.service;

import com.evo.common.dto.request.GetPaymentUrlRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentQueryService {
    String getPaymentUrl(GetPaymentUrlRequest getPaymentUrlRequest, HttpServletRequest request);

}
