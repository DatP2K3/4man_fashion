package com.evo.payment.rest;

import com.evo.common.dto.request.GetPaymentUrlRequest;
import com.evo.common.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Payment API")
@RequestMapping("/api")
@Validated
public interface PaymentController {

    @Operation(summary = "Get payment URL")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/payment")
    Response<String> getPaymentUrl(
            @RequestBody GetPaymentUrlRequest getPaymentUrlRequest, HttpServletRequest httpServletRequest);

    @Operation(summary = "VN Pay callback handler")
    @GetMapping("/vn-pay-callback")
    Response<Void> payCallbackHandler(HttpServletRequest request, HttpServletResponse response);
}
