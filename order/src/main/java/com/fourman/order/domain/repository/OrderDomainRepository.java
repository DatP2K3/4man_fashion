package com.fourman.order.domain.repository;

import java.util.List;
import java.util.UUID;

import com.fourman.common.enums.OrderStatus;
import com.fourman.common.repository.DomainRepository;
import com.fourman.order.domain.Order;

public interface OrderDomainRepository extends DomainRepository<Order, UUID> {
    Order findByOrderCode(String orderCode);

    List<Order> getByIds(List<UUID> orderIds);

    Order getByOrderCode(String orderCode);

    List<Order> getAllOrderWithStatusIn(List<OrderStatus> orderStatuses);

    List<Order> getByGhnOrderCodeIn(List<String> orderCodes);
}
