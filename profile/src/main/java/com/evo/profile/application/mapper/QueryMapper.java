package com.evo.profile.application.mapper;

import org.mapstruct.Mapper;

import com.evo.profile.application.dto.request.SearchProfileRequest;
import com.evo.profile.domain.query.SearchProfileQuery;

@Mapper(componentModel = "spring")
public interface QueryMapper {
    SearchProfileQuery from(SearchProfileRequest searchProfileRequest);
}
