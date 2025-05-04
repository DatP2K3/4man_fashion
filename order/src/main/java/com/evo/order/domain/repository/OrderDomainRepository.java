package com.evo.order.domain.repository;

import com.evo.common.repository.DomainRepository;
import com.evo.order.domain.Order;

import java.util.UUID;

public interface OrderDomainRepository extends DomainRepository<Order, UUID> {
}
