package com.evo.shopinfo.infrastructure.persistence.repository;

import com.evo.shopinfo.infrastructure.persistence.entity.ShopAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShopAddressEntityRepository extends JpaRepository<ShopAddressEntity, UUID> {
}
