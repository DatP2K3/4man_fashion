package com.evo.order.domain.query;

import java.util.UUID;

import com.evo.common.enums.OrderStatus;
import com.evo.common.query.PagingQuery;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchOrderQuery extends PagingQuery {
    private String keyword;
    private UUID userId;
    private OrderStatus orderStatus;
}
