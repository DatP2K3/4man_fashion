package com.evo.order.infrastructure.domainrepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.evo.common.enums.OrderStatus;
import com.evo.common.repository.AbstractDomainRepository;
import com.evo.order.domain.Order;
import com.evo.order.domain.OrderItem;
import com.evo.order.domain.repository.OrderDomainRepository;
import com.evo.order.infrastructure.persistence.entity.OrderEntity;
import com.evo.order.infrastructure.persistence.entity.OrderItemEntity;
import com.evo.order.infrastructure.persistence.mapper.OrderEntityMapper;
import com.evo.order.infrastructure.persistence.mapper.OrderItemEntityMapper;
import com.evo.order.infrastructure.persistence.repository.OrderEntityRepository;
import com.evo.order.infrastructure.persistence.repository.OrderItemEntityRepository;
import com.evo.order.infrastructure.support.exception.AppErrorCode;
import com.evo.order.infrastructure.support.exception.AppException;

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
    public Order save(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderItemEntity> orderItemEntities = orderItemEntityMapper.toEntityList(orderItems);
        if (orderItemEntities != null && !orderItemEntities.isEmpty()) {
            orderItemEntityRepository.saveAll(orderItemEntities);
        }
        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        return this.enrich(orderEntityMapper.toDomainModel(orderEntityRepository.save(orderEntity)));
    }

    @Override
    public Order getById(UUID uuid) {
        OrderEntity orderEntity =
                orderEntityRepository.findById(uuid).orElseThrow(() -> new AppException(AppErrorCode.ORDER_NOT_FOUND));
        return this.enrich(orderEntityMapper.toDomainModel(orderEntity));
    }

    @Override
    protected List<Order> enrichList(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return orders;
        }

        List<UUID> orderIds = orders.stream().map(Order::getId).toList();

        Map<UUID, List<OrderItem>> orderItemMap = orderItemEntityRepository.findByOrderIdIn(orderIds).stream()
                .collect(Collectors.groupingBy(
                        OrderItemEntity::getOrderId,
                        Collectors.mapping(orderItemEntityMapper::toDomainModel, Collectors.toList())));

        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemMap.get(order.getId());
            if (orderItems != null) {
                order.setOrderItems(orderItems);
            }
        }

        return orders;
    }

    @Override
    public Order findByOrderCode(String orderCode) {
        OrderEntity orderEntity = orderEntityRepository
                .findByOrderCode(orderCode)
                .orElseThrow(() -> new AppException(AppErrorCode.ORDER_NOT_FOUND));
        return this.enrich(orderEntityMapper.toDomainModel(orderEntity));
    }

    @Override
    public List<Order> getByIds(List<UUID> orderIds) {
        List<OrderEntity> orderEntities = orderEntityRepository.findAllById(orderIds);
        return this.enrichList(orderEntityMapper.toDomainModelList(orderEntities));
    }

    @Override
    public Order getByOrderCode(String orderCode) {
        OrderEntity orderEntity = orderEntityRepository
                .findByOrderCode(orderCode)
                .orElseThrow(() -> new AppException(AppErrorCode.ORDER_NOT_FOUND));
        Order order = this.enrich(orderEntityMapper.toDomainModel(orderEntity));
        return order;
    }

    @Override
    public List<Order> getAllOrderWithStatusIn(List<OrderStatus> orderStatuses) {
        List<OrderEntity> orderEntities = orderEntityRepository.getAllOrderWithStatusIn(orderStatuses);
        return this.enrichList(orderEntityMapper.toDomainModelList(orderEntities));
    }
}
