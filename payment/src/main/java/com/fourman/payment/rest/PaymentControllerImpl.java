package com.fourman.payment.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

import com.fourman.common.dto.request.GetPaymentUrlRequest;
import com.fourman.common.dto.response.Response;
import com.fourman.payment.application.service.PaymentCommandService;
import com.fourman.payment.application.service.PaymentQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {
    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;

    @Override
    public Response<String> getPaymentUrl(
            @RequestBody GetPaymentUrlRequest getPaymentUrlRequest, HttpServletRequest httpServletRequest) {
        return Response.of(this.paymentQueryService.getPaymentUrl(getPaymentUrlRequest, httpServletRequest));
    }

    @Override
    public Response<Void> payCallbackHandler(HttpServletRequest request, HttpServletResponse response) {
        this.paymentCommandService.handlePaymentCallback(request, response);
        return Response.ok();
    }
}
