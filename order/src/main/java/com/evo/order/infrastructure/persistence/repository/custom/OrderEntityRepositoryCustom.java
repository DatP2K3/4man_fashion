package com.evo.order.infrastructure.persistence.repository.custom;

import com.evo.order.domain.query.SearchOrderQuery;
import com.evo.order.infrastructure.persistence.entity.OrderEntity;

import java.util.List;

public interface OrderEntityRepositoryCustom {
    List<OrderEntity> search(SearchOrderQuery searchOrderQuery);
    Long count(SearchOrderQuery searchOrderQuery);
}
