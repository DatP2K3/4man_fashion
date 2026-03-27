package com.fourman.profile.infrastructure.persistence.repository.custom;

import java.util.List;

import com.fourman.profile.domain.query.SearchProfileQuery;
import com.fourman.profile.infrastructure.persistence.entity.ProfileEntity;

public interface ProfileEntityRepositoryCustom {
    List<ProfileEntity> search(SearchProfileQuery searchUserQuery);

    Long count(SearchProfileQuery searchUserQuery);
}
