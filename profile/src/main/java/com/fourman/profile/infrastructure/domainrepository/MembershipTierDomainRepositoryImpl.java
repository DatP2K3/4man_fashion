package com.fourman.profile.infrastructure.domainrepository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fourman.common.exception.ResponseException;
import com.fourman.common.repository.AbstractDomainRepository;
import com.fourman.profile.domain.MembershipTier;
import com.fourman.profile.domain.exception.NotFoundError;
import com.fourman.profile.domain.repository.MembershipTierDomainRepository;
import com.fourman.profile.infrastructure.persistence.entity.MembershipTierEntity;
import com.fourman.profile.infrastructure.persistence.mapper.MembershipTierEntityMapper;
import com.fourman.profile.infrastructure.persistence.repository.MembershipTierEntityRepository;

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
                .orElseThrow(() -> new ResponseException(NotFoundError.MEMBERSHIP_TIER_NOT_FOUND)));
    }

    @Override
    public MembershipTier getDefaultMembershipTier() {
        return membershipTierEntityMapper.toDomainModel(
                membershipTierEntityRepository.findByDefaultTierTrue().orElse(null));
    }

    @Override
    public MembershipTier getNextTier(Integer minPoints) {
        return membershipTierEntityMapper.toDomainModel(membershipTierEntityRepository
                .findNextTierByMinPoints(minPoints)
                .getFirst());
    }
}
