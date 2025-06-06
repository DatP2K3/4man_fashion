package com.evo.profile.application.service.impl.query;

import com.evo.common.dto.response.MembershipTierDTO;
import com.evo.profile.application.dto.mapper.MembershipTierDTOMapper;
import com.evo.profile.application.service.MembershipTierQueryService;
import com.evo.profile.infrastructure.persistence.mapper.MembershipTierEntityMapper;
import com.evo.profile.infrastructure.persistence.repository.MembershipTierEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipTierQueryServiceImpl implements MembershipTierQueryService {
    private final MembershipTierEntityRepository membershipTierEntityRepository;
    private final MembershipTierDTOMapper membershipTierDTOMapper;
    @Override
    public List<MembershipTierDTO> findAll() {
        return membershipTierDTOMapper.entitiesToDTOs(membershipTierEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "minPoints")));
    }
}
