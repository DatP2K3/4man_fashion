package com.fourman.profile.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.profile.application.dto.request.SearchProfileRequest;
import com.fourman.profile.domain.query.SearchProfileQuery;

@Mapper(componentModel = "spring")
public interface QueryMapper {
    SearchProfileQuery from(SearchProfileRequest searchProfileRequest);
}
