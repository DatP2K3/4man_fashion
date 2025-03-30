package com.evo.profile.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.profile.infrastructure.persistence.entity.ProfileEntity;

public interface ProfileEntityRepository extends JpaRepository<ProfileEntity, UUID> {}
