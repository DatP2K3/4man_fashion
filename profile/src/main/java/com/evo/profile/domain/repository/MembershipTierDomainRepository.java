package com.evo.profile.domain.repository;

import java.util.UUID;

import com.evo.common.repository.DomainRepository;
import com.evo.profile.domain.MembershipTier;

public interface MembershipTierDomainRepository extends DomainRepository<MembershipTier, UUID> {
    MembershipTier getById(UUID membershipTierId);

    MembershipTier getDefaultMembershipTier();

    MembershipTier getNextTier(Integer minPoints);
}
