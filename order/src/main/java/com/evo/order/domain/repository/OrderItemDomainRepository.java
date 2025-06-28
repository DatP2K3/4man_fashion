package com.evo.order.domain.repository;

import java.util.UUID;

import com.evo.common.repository.DomainRepository;
import com.evo.order.domain.OrderItem;

public interface OrderItemDomainRepository extends DomainRepository<OrderItem, UUID> {}
