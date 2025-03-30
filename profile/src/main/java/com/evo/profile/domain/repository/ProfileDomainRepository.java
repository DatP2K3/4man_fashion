package com.evo.profile.domain.repository;

import java.util.UUID;

import com.evo.common.repository.DomainRepository;
import com.evo.profile.domain.Profile;

public interface ProfileDomainRepository extends DomainRepository<Profile, UUID> {
    Profile getById(UUID profileId);
}
