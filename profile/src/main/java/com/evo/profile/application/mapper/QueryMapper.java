package com.evo.profile.application.mapper;

import com.evo.profile.application.dto.request.SearchProfileRequest;
import com.evo.profile.domain.query.SearchProfileQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QueryMapper {
    SearchProfileQuery from(SearchProfileRequest searchProfileRequest);
}
