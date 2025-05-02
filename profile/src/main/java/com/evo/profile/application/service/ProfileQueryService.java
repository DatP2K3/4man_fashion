package com.evo.profile.application.service;

import java.util.List;

import com.evo.profile.application.dto.request.SearchProfileRequest;
import com.evo.profile.application.dto.response.ProfileDTO;

public interface ProfileQueryService {
    Long totalProfiles(SearchProfileRequest searchProfileRequest);

    List<ProfileDTO> searchProfiles(SearchProfileRequest searchProfileRequest);
}
