package com.evo.order.domain.command;

import com.evo.common.enums.TransactionStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusCmd {
    private String orderCode;
    private TransactionStatus status;
}
