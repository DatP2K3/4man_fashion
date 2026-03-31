package com.fourman.payment.application.service;

import java.util.Map;

public interface PaymentCommandService {
    String handlePaymentCallback(Map<String, String> params);
}
