package com.evo.order.infrastructure.domainrepository;

import com.evo.common.repository.AbstractDomainRepository;
import com.evo.order.domain.Order;
import com.evo.order.domain.repository.OrderDomainRepository;
import com.evo.order.infrastructure.persistence.entity.OrderEntity;
import com.evo.order.infrastructure.persistence.mapper.OrderEntityMapper;
import com.evo.order.infrastructure.persistence.mapper.OrderItemEntityMapper;
import com.evo.order.infrastructure.persistence.repository.OrderEntityRepository;
import com.evo.order.infrastructure.persistence.repository.OrderItemEntityRepository;
import com.evo.order.infrastructure.support.exception.AppErrorCode;
import com.evo.order.infrastructure.support.exception.AppException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class OrderDomainRepositoryImpl extends AbstractDomainRepository<Order, OrderEntity, UUID>
        implements OrderDomainRepository {
    private final OrderEntityMapper orderEntityMapper;
    private final OrderEntityRepository orderEntityRepository;
    private final OrderItemEntityMapper orderItemEntityMapper;
    private final OrderItemEntityRepository orderItemEntityRepository;

    public OrderDomainRepositoryImpl(
            OrderEntityMapper orderEntityMapper,
            OrderEntityRepository orderEntityRepository,
            OrderItemEntityMapper orderItemEntityMapper,
            OrderItemEntityRepository orderItemEntityRepository) {
        super(orderEntityRepository, orderEntityMapper);
        this.orderEntityMapper = orderEntityMapper;
        this.orderEntityRepository = orderEntityRepository;
        this.orderItemEntityMapper = orderItemEntityMapper;
        this.orderItemEntityRepository = orderItemEntityRepository;
    }

    @Override
    public Order getById(UUID uuid) {
        OrderEntity orderEntity = orderEntityRepository.findById(uuid).orElseThrow(() -> new AppException(AppErrorCode.ORDER_NOT_FOUND));
        return orderEntityMapper.toDomainModel(orderEntity);
    }

    @Override
    protected List<Order> enrichList(List<Order> orders) {
        return super.enrichList(orders);
    }
}
