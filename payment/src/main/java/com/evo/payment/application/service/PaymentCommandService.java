package com.evo.payment.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface PaymentCommandService {
    void handlePaymentCallback(HttpServletRequest request, HttpServletResponse response);
}
