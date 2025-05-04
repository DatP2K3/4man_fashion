package com.evo.profile.application.service.impl.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.evo.profile.application.dto.mapper.ProfileDTOMapper;
import com.evo.profile.application.dto.request.SearchProfileRequest;
import com.evo.common.dto.response.ProfileDTO;
import com.evo.profile.application.mapper.QueryMapper;
import com.evo.profile.application.service.ProfileQueryService;
import com.evo.profile.domain.Profile;
import com.evo.profile.domain.query.SearchProfileQuery;
import com.evo.profile.domain.repository.ProfileDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileDomainRepository profileDomainRepository;
    private final QueryMapper queryMapper;
    private final ProfileDTOMapper profileDTOMapper;

    @Override
    public Long totalProfiles(SearchProfileRequest searchProfileRequest) {
        SearchProfileQuery query = queryMapper.from(searchProfileRequest);
        return profileDomainRepository.count(query);
    }

    @Override
    public List<ProfileDTO> searchProfiles(SearchProfileRequest searchProfileRequest) {
        SearchProfileQuery query = queryMapper.from(searchProfileRequest);
        List<Profile> profiles = profileDomainRepository.search(query);
        return profileDTOMapper.domainModelsToDTOs(profiles);
    }
}
