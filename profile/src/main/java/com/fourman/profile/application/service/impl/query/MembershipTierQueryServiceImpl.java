package com.fourman.profile.application.service.impl.query;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.common.dto.response.MembershipTierDTO;
import com.fourman.profile.application.dto.mapper.MembershipTierDTOMapper;
import com.fourman.profile.application.service.MembershipTierQueryService;
import com.fourman.profile.infrastructure.persistence.repository.MembershipTierEntityRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
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
