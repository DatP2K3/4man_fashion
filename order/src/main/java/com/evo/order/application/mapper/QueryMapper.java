package com.evo.order.application.mapper;

import com.evo.order.application.dto.request.SearchOrderRequest;
import com.evo.order.domain.query.SearchOrderQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QueryMapper {
    SearchOrderQuery from(SearchOrderRequest searchFileRequest);
}
