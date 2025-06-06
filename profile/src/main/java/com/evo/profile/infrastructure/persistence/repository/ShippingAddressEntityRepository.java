package com.evo.profile.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.profile.infrastructure.persistence.entity.ShippingAddressEntity;

public interface ShippingAddressEntityRepository extends JpaRepository<ShippingAddressEntity, UUID> {
    List<ShippingAddressEntity> findByProfileIdIn(List<UUID> profileIds);

    Optional<ShippingAddressEntity> findByDefaultAddressTrue();
}
