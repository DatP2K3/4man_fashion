package com.evo.payment.domain;

import java.time.Instant;

import com.evo.common.enums.TransactionStatus;
import com.evo.payment.domain.command.CreatePaymentTransactionCmd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
