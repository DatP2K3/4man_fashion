package com.fourman.profile.domain.repository;

import java.util.List;
import java.util.UUID;

import com.fourman.common.repository.DomainRepository;
import com.fourman.profile.domain.Profile;
import com.fourman.profile.domain.ShippingAddress;
import com.fourman.profile.domain.query.SearchProfileQuery;

public interface ProfileDomainRepository extends DomainRepository<Profile, UUID> {
    Profile getById(UUID profileId);

    Profile getByIdOrNull(UUID userId);

    ShippingAddress getDefaultShippingAddress();

    List<Profile> search(SearchProfileQuery searchUserQuery);

    Long count(SearchProfileQuery searchUserQuery);

    List<Profile> getAll();
}
