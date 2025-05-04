package com.evo.order.domain.repository;

import com.evo.common.repository.DomainRepository;
import com.evo.order.domain.OrderItem;

import java.util.UUID;

public interface OrderItemDomainRepository  extends DomainRepository<OrderItem, UUID> {
}
