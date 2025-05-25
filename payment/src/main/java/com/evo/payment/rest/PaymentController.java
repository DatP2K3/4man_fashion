package com.evo.payment.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.request.GetPaymentUrlRequest;
import com.evo.common.dto.response.ApiResponses;
import com.evo.payment.application.service.PaymentCommandService;
import com.evo.payment.application.service.PaymentQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/payment")
    ApiResponses<String> getPaymentUrl(
            @RequestBody GetPaymentUrlRequest getPaymentUrlRequest, HttpServletRequest httpServletRequest) {
        String paymentUrl = paymentQueryService.getPaymentUrl(getPaymentUrlRequest, httpServletRequest);
        return ApiResponses.<String>builder()
                .data(paymentUrl)
                .success(true)
                .code(201)
                .message("PaymentUrl created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/vn-pay-callback")
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) {
        paymentCommandService.handlePaymentCallback(request, response);
    }
}
