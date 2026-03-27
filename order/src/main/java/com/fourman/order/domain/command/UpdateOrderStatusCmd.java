package com.fourman.order.domain.command;

import com.fourman.common.enums.TransactionStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusCmd {
    private String orderCode;
    private TransactionStatus status;
}
