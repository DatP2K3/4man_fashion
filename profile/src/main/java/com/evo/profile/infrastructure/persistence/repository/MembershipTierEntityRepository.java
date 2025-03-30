package com.evo.profile.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.profile.infrastructure.persistence.entity.MembershipTierEntity;

public interface MembershipTierEntityRepository extends JpaRepository<MembershipTierEntity, UUID> {
    Optional<MembershipTierEntity> findByDefaultTierTrue();
}
