package com.evo.order.domain.repository;

import com.evo.common.enums.OrderStatus;
import com.evo.common.repository.DomainRepository;
import com.evo.order.domain.Order;

import java.util.List;
import java.util.UUID;

public interface OrderDomainRepository extends DomainRepository<Order, UUID> {
    Order findByOrderCode(String orderCode);
    List<Order> getByIds(List<UUID> orderIds);

    List<Order> getAllOrderWithStatusIn(List<OrderStatus> orderStatuses);
}
