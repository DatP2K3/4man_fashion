package com.evo.order.domain.repository;

import java.util.List;
import java.util.UUID;

import com.evo.common.enums.OrderStatus;
import com.evo.common.repository.DomainRepository;
import com.evo.order.domain.Order;

public interface OrderDomainRepository extends DomainRepository<Order, UUID> {
    Order findByOrderCode(String orderCode);

    List<Order> getByIds(List<UUID> orderIds);

    Order getByOrderCode(String orderCode);

    List<Order> getAllOrderWithStatusIn(List<OrderStatus> orderStatuses);

    List<Order> getByGHNOrderCodeIn(List<String> orderCodes);
}
