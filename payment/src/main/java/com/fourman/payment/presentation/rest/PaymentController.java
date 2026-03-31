package com.fourman.payment.presentation.rest;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fourman.common.dto.request.GetPaymentUrlRequest;
import com.fourman.common.dto.response.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payment API")
@RequestMapping("/api")
@Validated
public interface PaymentController {

    @Operation(summary = "Get payment URL")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/payment")
    Response<String> getPaymentUrl(
            @Valid @RequestBody GetPaymentUrlRequest getPaymentUrlRequest, HttpServletRequest httpServletRequest);

    @Operation(summary = "VN Pay callback handler")
    @GetMapping("/vn-pay-callback")
    ResponseEntity<Void> payCallbackHandler(@RequestParam Map<String, String> params);
}
