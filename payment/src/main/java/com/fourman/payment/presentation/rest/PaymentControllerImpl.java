package com.fourman.payment.presentation.rest;

import java.util.Map;
import java.net.URI;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

import com.fourman.common.dto.request.GetPaymentUrlRequest;
import com.fourman.common.dto.response.Response;
import com.fourman.common.webapp.config.inbound.InboundRequest;
import com.fourman.payment.application.service.PaymentCommandService;
import com.fourman.payment.application.service.PaymentQueryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fourman.payment.infrastructure.support.VNPayUtil;

import lombok.RequiredArgsConstructor;

@InboundRequest
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {
    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;

    @Override
    public Response<String> getPaymentUrl(
            @RequestBody GetPaymentUrlRequest getPaymentUrlRequest, HttpServletRequest httpServletRequest) {
        String clientIp = VNPayUtil.getIpAddress(httpServletRequest);
        return Response.of(this.paymentQueryService.getPaymentUrl(getPaymentUrlRequest, clientIp));
    }

    @Override
    public ResponseEntity<Void> payCallbackHandler(@RequestParam Map<String, String> params) {
        String redirectUrl = this.paymentCommandService.handlePaymentCallback(params);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }
}
