package com.fourman.profile.domain.repository;

import java.util.UUID;

import com.fourman.common.repository.DomainRepository;
import com.fourman.profile.domain.MembershipTier;

public interface MembershipTierDomainRepository extends DomainRepository<MembershipTier, UUID> {
    MembershipTier getById(UUID membershipTierId);

    MembershipTier getDefaultMembershipTier();

    MembershipTier getNextTier(Integer minPoints);
}
