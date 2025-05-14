package com.evo.payment.domain.command;

import java.time.Instant;

import com.evo.common.enums.TransactionStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreatePaymentTransactionCmd {
    private Long amount;
    private String transactionCode;
    private String orderCode;
    private TransactionStatus status;
    private Instant payDate;
    private String transactionInfo;
}
