package com.fourman.profile.application.service;

import com.fourman.common.dto.response.PageDTO;
import com.fourman.common.dto.response.ProfileDTO;
import com.fourman.profile.application.dto.request.SearchProfileRequest;
import com.fourman.profile.domain.query.SearchProfileQuery;

public interface ProfileQueryService {
    Long totalProfiles(SearchProfileQuery searchProfileQuery);

    PageDTO<ProfileDTO> searchProfiles(SearchProfileRequest searchProfileRequest);
}
