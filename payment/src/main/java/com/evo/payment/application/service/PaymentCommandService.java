package com.evo.payment.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface PaymentCommandService {
    void handlePaymentCallback(HttpServletRequest request, HttpServletResponse response);
}
