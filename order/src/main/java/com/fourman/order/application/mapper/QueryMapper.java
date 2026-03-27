package com.fourman.order.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.request.SearchOrderRequest;
import com.fourman.order.domain.query.SearchOrderQuery;

@Mapper(componentModel = "spring")
public interface QueryMapper {
    SearchOrderQuery from(SearchOrderRequest searchFileRequest);
}
