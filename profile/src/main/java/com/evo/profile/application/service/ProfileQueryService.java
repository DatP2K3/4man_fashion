package com.evo.profile.application.service;

import com.evo.profile.application.dto.request.SearchProfileRequest;
import com.evo.profile.application.dto.response.ProfileDTO;

import java.util.List;

public interface ProfileQueryService {
    Long totalProfiles(SearchProfileRequest searchProfileRequest);
    List<ProfileDTO> searchProfiles(SearchProfileRequest searchProfileRequest);
}
