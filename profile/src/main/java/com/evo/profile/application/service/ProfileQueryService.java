package com.evo.profile.application.service;

import java.util.List;

import com.evo.common.dto.response.PageDTO;
import com.evo.common.dto.response.ProfileDTO;
import com.evo.profile.application.dto.request.SearchProfileRequest;
import com.evo.profile.domain.query.SearchProfileQuery;

public interface ProfileQueryService {
    Long totalProfiles(SearchProfileQuery searchProfileQuery);

    PageDTO<ProfileDTO> searchProfiles(SearchProfileRequest searchProfileRequest);
}
