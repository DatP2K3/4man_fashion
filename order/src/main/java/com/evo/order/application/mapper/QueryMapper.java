package com.evo.order.application.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.request.SearchOrderRequest;
import com.evo.order.domain.query.SearchOrderQuery;

@Mapper(componentModel = "spring")
public interface QueryMapper {
    SearchOrderQuery from(SearchOrderRequest searchFileRequest);
}
