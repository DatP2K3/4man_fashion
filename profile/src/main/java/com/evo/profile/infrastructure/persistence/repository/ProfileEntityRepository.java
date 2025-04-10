package com.evo.profile.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import com.evo.profile.infrastructure.persistence.repository.custom.ProfileEntityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.profile.infrastructure.persistence.entity.ProfileEntity;

public interface ProfileEntityRepository extends JpaRepository<ProfileEntity, UUID>, ProfileEntityRepositoryCustom {
    Optional<ProfileEntity> getByUsername(String username);
}
