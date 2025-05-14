package com.evo.profile.application.service;

import java.util.List;

import com.evo.common.dto.response.ProfileDTO;
import com.evo.profile.application.dto.request.SearchProfileRequest;

public interface ProfileQueryService {
    Long totalProfiles(SearchProfileRequest searchProfileRequest);

    List<ProfileDTO> searchProfiles(SearchProfileRequest searchProfileRequest);
}
