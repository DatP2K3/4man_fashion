package com.fourman.storage.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.storage.application.dto.request.SearchFileRequest;
import com.fourman.storage.domain.query.SearchFileQuery;

@Mapper(componentModel = "spring")
public interface QueryMapper {
    SearchFileQuery from(SearchFileRequest searchFileRequest);
}
