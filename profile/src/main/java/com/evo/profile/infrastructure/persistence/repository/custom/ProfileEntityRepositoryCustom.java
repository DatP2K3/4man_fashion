package com.evo.profile.infrastructure.persistence.repository.custom;

import java.util.List;

import com.evo.profile.domain.query.SearchProfileQuery;
import com.evo.profile.infrastructure.persistence.entity.ProfileEntity;

public interface ProfileEntityRepositoryCustom {
    List<ProfileEntity> search(SearchProfileQuery searchUserQuery);

    Long count(SearchProfileQuery searchUserQuery);
}
