package com.fourman.order.domain.repository;

import java.util.UUID;

import com.fourman.common.repository.DomainRepository;
import com.fourman.order.domain.OrderItem;

public interface OrderItemDomainRepository extends DomainRepository<OrderItem, UUID> {}
