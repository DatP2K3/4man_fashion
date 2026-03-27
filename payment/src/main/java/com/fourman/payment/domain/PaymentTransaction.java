package com.fourman.payment.domain;

import java.time.Instant;

import com.fourman.common.enums.TransactionStatus;
import com.fourman.payment.domain.command.CreatePaymentTransactionCmd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTransaction {
    private Long amount;
    private String transactionCode;
    private String orderCode;
    private TransactionStatus status;
    private Instant payDate;
    private String transactionInfo;

    public PaymentTransaction(CreatePaymentTransactionCmd cmd) {
        this.amount = cmd.getAmount();
        this.transactionCode = cmd.getTransactionCode();
        this.orderCode = cmd.getOrderCode();
        this.status = cmd.getStatus();
        this.payDate = cmd.getPayDate();
        this.transactionInfo = cmd.getTransactionInfo();
    }
}
