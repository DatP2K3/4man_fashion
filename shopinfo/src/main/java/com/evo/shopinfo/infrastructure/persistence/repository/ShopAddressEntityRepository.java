package com.evo.shopinfo.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.shopinfo.infrastructure.persistence.entity.ShopAddressEntity;

public interface ShopAddressEntityRepository extends JpaRepository<ShopAddressEntity, UUID> {}
