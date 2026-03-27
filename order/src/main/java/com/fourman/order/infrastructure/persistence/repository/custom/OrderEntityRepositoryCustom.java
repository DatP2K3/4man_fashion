package com.fourman.order.infrastructure.persistence.repository.custom;

import java.util.List;

import com.fourman.order.domain.query.SearchOrderQuery;
import com.fourman.order.infrastructure.persistence.entity.OrderEntity;

public interface OrderEntityRepositoryCustom {
    List<OrderEntity> search(SearchOrderQuery searchOrderQuery);

    Long count(SearchOrderQuery searchOrderQuery);
}
