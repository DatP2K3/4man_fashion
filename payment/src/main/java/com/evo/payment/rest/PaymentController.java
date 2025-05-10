package com.evo.payment.rest;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.request.GetPaymentUrlRequest;
import com.evo.payment.application.service.PaymentCommandService;
import com.evo.payment.application.service.PaymentQueryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {
     private final PaymentCommandService paymentCommandService;
     private final PaymentQueryService paymentQueryService;

     @PostMapping("/payment")
     ApiResponses<String> getPaymentUrl(@RequestBody GetPaymentUrlRequest getPaymentUrlRequest, HttpServletRequest httpServletRequest) {
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
}
