package com.evo.profile.application.service.impl.command;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.evo.profile.application.dto.mapper.MembershipTierDTOMapper;
import com.evo.profile.application.dto.request.CreateOrUpdateMembershipTierRequest;
import com.evo.common.dto.response.MembershipTierDTO;
import com.evo.profile.application.mapper.CommandMapper;
import com.evo.profile.application.service.MembershipTierService;
import com.evo.profile.domain.MembershipTier;
import com.evo.profile.domain.command.CreateOrUpdateMembershipTierCmd;
import com.evo.profile.domain.repository.MembershipTierDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembershipTierServiceImpl implements MembershipTierService {
    private final MembershipTierDomainRepository membershipTierDomainRepository;
    private final CommandMapper commandMapper;
    private final MembershipTierDTOMapper membershipTierDTOMapper;

    @Override
    public MembershipTierDTO create(CreateOrUpdateMembershipTierRequest createMemberShipTierRequest) {
        CreateOrUpdateMembershipTierCmd createMembershipTierCmd =
                commandMapper.fromCreateOrUpdateProfileRequest(createMemberShipTierRequest);
        if (createMembershipTierCmd.getDefaultTier()) {
            MembershipTier defaultMembershipTier = membershipTierDomainRepository.getDefaultMembershipTier();
            if (defaultMembershipTier != null) {
                defaultMembershipTier.setDefaultTier(false);
                membershipTierDomainRepository.save(defaultMembershipTier);
            }
        }
        MembershipTier membershipTier = new MembershipTier(createMembershipTierCmd);
        membershipTier = membershipTierDomainRepository.save(membershipTier);
        return membershipTierDTOMapper.domainModelToDTO(membershipTier);
    }

    @Override
    public MembershipTierDTO update(CreateOrUpdateMembershipTierRequest updateMemberShipTierRequest) {
        CreateOrUpdateMembershipTierCmd updateMembershipTierCmd =
                commandMapper.fromCreateOrUpdateProfileRequest(updateMemberShipTierRequest);
        if (updateMembershipTierCmd.getDefaultTier()) {
            MembershipTier defaultMembershipTier = membershipTierDomainRepository.getDefaultMembershipTier();
            if (defaultMembershipTier != null) {
                defaultMembershipTier.setDefaultTier(false);
                membershipTierDomainRepository.save(defaultMembershipTier);
            }
        }
        MembershipTier membershipTier = membershipTierDomainRepository.getById(updateMembershipTierCmd.getId());
        membershipTier.update(updateMembershipTierCmd);
        membershipTier = membershipTierDomainRepository.save(membershipTier);
        return membershipTierDTOMapper.domainModelToDTO(membershipTier);
    }

    @Override
    public UUID getDefaultMembershipTierId() {
        return membershipTierDomainRepository.getDefaultMembershipTier().getId();
    }

    @Override
    public void delete(UUID id, boolean deleted) {
        MembershipTier membershipTier = membershipTierDomainRepository.getById(id);
        membershipTier.setDeleted(deleted);
        membershipTierDomainRepository.save(membershipTier);
    }
}
