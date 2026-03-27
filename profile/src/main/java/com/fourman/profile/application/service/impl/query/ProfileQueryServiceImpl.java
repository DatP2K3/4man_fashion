package com.fourman.profile.application.service.impl.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fourman.common.dto.response.PageDTO;
import com.fourman.common.dto.response.ProfileDTO;
import com.fourman.profile.application.dto.mapper.ProfileDTOMapper;
import com.fourman.profile.application.dto.request.SearchProfileRequest;
import com.fourman.profile.application.mapper.QueryMapper;
import com.fourman.profile.application.service.ProfileQueryService;
import com.fourman.profile.domain.Profile;
import com.fourman.profile.domain.query.SearchProfileQuery;
import com.fourman.profile.domain.repository.ProfileDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileDomainRepository profileDomainRepository;
    private final QueryMapper queryMapper;
    private final ProfileDTOMapper profileDTOMapper;

    @Override
    public Long totalProfiles(SearchProfileQuery searchProfileQuery) {
        return profileDomainRepository.count(searchProfileQuery);
    }

    @Override
    public PageDTO<ProfileDTO> searchProfiles(SearchProfileRequest searchProfileRequest) {
        SearchProfileQuery query = queryMapper.from(searchProfileRequest);
        Long totalProfiles = this.totalProfiles(query);
        if (totalProfiles == 0) {
            return PageDTO.empty();
        }

        List<Profile> profiles = profileDomainRepository.search(query);
        return PageDTO.of(
                profileDTOMapper.domainModelsToDTOs(profiles),
                query.getPageIndex(),
                query.getPageSize(),
                totalProfiles);
    }
}
