package com.evo.payment.application.service.impl.command;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.OrderEvent;
import com.evo.common.enums.TransactionStatus;
import com.evo.payment.application.service.PaymentCommandService;
import com.evo.payment.domain.PaymentTransaction;
import com.evo.payment.domain.command.CreatePaymentTransactionCmd;
import com.evo.payment.domain.repository.PaymentTransactionDomainRepository;
import com.evo.payment.infrastructure.adapter.rabbitmq.OrderEventRabbitMQService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentCommanđServiceImpl implements PaymentCommandService {
    private final PaymentTransactionDomainRepository paymentTransactionDomainRepository;
    private final OrderEventRabbitMQService orderEventRabbitMQService;

    @Value("${payment.redirectUrl}")
    private String redirectUrl;

    @Override
    public void handlePaymentCallback(HttpServletRequest request, HttpServletResponse response) {
        Long amount = Long.parseLong(request.getParameter("vnp_Amount"));
        String orderCode = request.getParameter("vnp_TxnRef");
        String transactionStatus = request.getParameter("vnp_ResponseCode");
        String transactionCode = request.getParameter("vnp_TransactionNo");
        String transactionDate = request.getParameter("vnp_PayDate");
        String transactionInfo = request.getParameter("vnp_OrderInfo");
        TransactionStatus status = null;

        if (transactionStatus.equals("00")) {
            status = TransactionStatus.SUCCESS;
        } else {
            status = TransactionStatus.FAIL;
        }

        Instant formattedPayDate = formatPayDate(transactionDate);

        CreatePaymentTransactionCmd cmd = CreatePaymentTransactionCmd.builder()
                .amount(amount)
                .transactionCode(transactionCode)
                .orderCode(orderCode)
                .status(status)
                .payDate(formattedPayDate)
                .transactionInfo(transactionInfo)
                .build();

        PaymentTransaction paymentTransaction = new PaymentTransaction(cmd);
        paymentTransactionDomainRepository.save(paymentTransaction);
        OrderEvent orderEvent =
                OrderEvent.builder().orderCode(orderCode).status(status).build();

        orderEventRabbitMQService.publishOrderUpdateEvent(orderEvent);

        String queryParams = String.format(
                "?status=%s&orderCode=%s&amount=%d&transactionCode=%s&payDate=%s&transactionInfo=%s",
                encode(status.name()),
                encode(orderCode),
                amount,
                encode(transactionCode),
                encode(transactionDate),
                encode(transactionInfo));

        try {
            response.sendRedirect(redirectUrl + queryParams);
        } catch (IOException e) {
            throw new RuntimeException("Failed to redirect to payment result page", e);
        }
    }

    private String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            return value;
        }
    }

    private Instant formatPayDate(String vnpayDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime localDateTime = LocalDateTime.parse(vnpayDate, formatter);

            ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
            return localDateTime.atZone(zoneId).toInstant();
        } catch (Exception e) {
            return Instant.now();
        }
    }
}
