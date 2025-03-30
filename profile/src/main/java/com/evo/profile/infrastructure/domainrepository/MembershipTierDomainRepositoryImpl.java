package com.evo.profile.infrastructure.domainrepository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.evo.common.repository.AbstractDomainRepository;
import com.evo.profile.domain.MembershipTier;
import com.evo.profile.domain.repository.MembershipTierDomainRepository;
import com.evo.profile.infrastructure.persistence.entity.MembershipTierEntity;
import com.evo.profile.infrastructure.persistence.mapper.MembershipTierEntityMapper;
import com.evo.profile.infrastructure.persistence.repository.MembershipTierEntityRepository;
import com.evo.profile.infrastructure.support.exception.AppErrorCode;
import com.evo.profile.infrastructure.support.exception.AppException;

@Repository
public class MembershipTierDomainRepositoryImpl
        extends AbstractDomainRepository<MembershipTier, MembershipTierEntity, UUID>
        implements MembershipTierDomainRepository {
    private final MembershipTierEntityMapper membershipTierEntityMapper;
    private final MembershipTierEntityRepository membershipTierEntityRepository;

    public MembershipTierDomainRepositoryImpl(
            MembershipTierEntityRepository membershipTierEntityRepository,
            MembershipTierEntityMapper membershipTierEntityMapper) {
        super(membershipTierEntityRepository, membershipTierEntityMapper);
        this.membershipTierEntityRepository = membershipTierEntityRepository;
        this.membershipTierEntityMapper = membershipTierEntityMapper;
    }

    @Override
    public MembershipTier getById(UUID membershipTierId) {
        return membershipTierEntityMapper.toDomainModel(membershipTierEntityRepository
                .findById(membershipTierId)
                .orElseThrow(() -> new AppException(AppErrorCode.MEMBERSHIP_TIER_NOT_FOUND)));
    }

    @Override
    public MembershipTier getDefaultMembershipTier() {
        return membershipTierEntityMapper.toDomainModel(
                membershipTierEntityRepository.findByDefaultTierTrue().orElse(null));
    }
}
