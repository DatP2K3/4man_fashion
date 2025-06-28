package com.evo.payment.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.request.GetPaymentUrlRequest;
import com.evo.common.dto.response.ApiResponses;
import com.evo.payment.application.service.PaymentCommandService;
import com.evo.payment.application.service.PaymentQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {
    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;

    @Override
    public ApiResponses<String> getPaymentUrl(
            @RequestBody GetPaymentUrlRequest getPaymentUrlRequest, HttpServletRequest httpServletRequest) {
        return ApiResponses.of(this.paymentQueryService.getPaymentUrl(getPaymentUrlRequest, httpServletRequest));
    }

    @Override
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) {
        this.paymentCommandService.handlePaymentCallback(request, response);
    }
}
