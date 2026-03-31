package com.fourman.payment.application.service.impl.command;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Map;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;

import com.fourman.common.webapp.support.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.common.dto.event.OrderEvent;
import com.fourman.common.enums.TransactionStatus;
import com.fourman.payment.application.service.PaymentCommandService;
import com.fourman.payment.domain.PaymentTransaction;
import com.fourman.payment.domain.command.CreatePaymentTransactionCmd;
import com.fourman.payment.domain.repository.PaymentTransactionDomainRepository;
import com.fourman.payment.infrastructure.adapter.rabbitmq.OrderEventRabbitMQService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class PaymentCommandServiceImpl implements PaymentCommandService {
    private final PaymentTransactionDomainRepository paymentTransactionDomainRepository;
    private final OrderEventRabbitMQService orderEventRabbitMQService;

    @Value("${payment.redirectUrl}")
    private String redirectUrl;

    @Override
    public String handlePaymentCallback(Map<String, String> params) {
        String amountStr = params.get("vnp_Amount");
        Long amount = amountStr != null ? Long.parseLong(amountStr) : 0L;
        String orderCode = params.get("vnp_TxnRef");
        String transactionStatus = params.get("vnp_ResponseCode");
        String transactionCode = params.get("vnp_TransactionNo");
        String transactionDate = params.get("vnp_PayDate");
        String transactionInfo = params.get("vnp_OrderInfo");

        TransactionStatus status = "00".equals(transactionStatus) ? TransactionStatus.SUCCESS : TransactionStatus.FAIL;

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
                encode(orderCode != null ? orderCode : ""),
                amount,
                encode(transactionCode != null ? transactionCode : ""),
                encode(transactionDate != null ? transactionDate : ""),
                encode(transactionInfo != null ? transactionInfo : ""));

        return redirectUrl + queryParams;
    }

    private String encode(String value) {
        if (value == null) return "";
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private Instant formatPayDate(String vnpayDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime localDateTime = LocalDateTime.parse(vnpayDate, formatter);

            return DateUtils.toInstant(localDateTime);
        } catch (DateTimeParseException e) {
            log.warn("Failed to parse VNPay date '{}', falling back to current time", vnpayDate, e);
            return Instant.now();
        }
    }
}
