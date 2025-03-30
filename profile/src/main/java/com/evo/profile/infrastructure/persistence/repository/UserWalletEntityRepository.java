package com.evo.profile.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.profile.infrastructure.persistence.entity.UserWalletEntity;

public interface UserWalletEntityRepository extends JpaRepository<UserWalletEntity, UUID> {
    List<UserWalletEntity> findByProfileIdIn(List<UUID> profileIds);
}
