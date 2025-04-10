package com.evo.profile.infrastructure.persistence.repository.custom;

import com.evo.profile.domain.query.SearchProfileQuery;
import com.evo.profile.infrastructure.persistence.entity.ProfileEntity;

import java.util.List;

public interface ProfileEntityRepositoryCustom {
    List<ProfileEntity> search(SearchProfileQuery searchUserQuery);

    Long count(SearchProfileQuery searchUserQuery);
}
