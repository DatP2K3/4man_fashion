package com.evo.profile.application.service.impl.query;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.evo.common.dto.response.MembershipTierDTO;
import com.evo.profile.application.dto.mapper.MembershipTierDTOMapper;
import com.evo.profile.application.service.MembershipTierQueryService;
import com.evo.profile.infrastructure.persistence.repository.MembershipTierEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembershipTierQueryServiceImpl implements MembershipTierQueryService {
    private final MembershipTierEntityRepository membershipTierEntityRepository;
    private final MembershipTierDTOMapper membershipTierDTOMapper;

    @Override
    public List<MembershipTierDTO> findAll() {
        return membershipTierDTOMapper.entitiesToDTOs(
                membershipTierEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "minPoints")));
    }
}
