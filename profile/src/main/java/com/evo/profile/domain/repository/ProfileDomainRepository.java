package com.evo.profile.domain.repository;

import java.util.List;
import java.util.UUID;

import com.evo.common.repository.DomainRepository;
import com.evo.profile.domain.Profile;
import com.evo.profile.domain.ShippingAddress;
import com.evo.profile.domain.query.SearchProfileQuery;

public interface ProfileDomainRepository extends DomainRepository<Profile, UUID> {
    Profile getById(UUID profileId);

    Profile getByIdOrNull(UUID userId);

    ShippingAddress getDefaultShippingAddress();

    List<Profile> search(SearchProfileQuery searchUserQuery);

    Long count(SearchProfileQuery searchUserQuery);
}
