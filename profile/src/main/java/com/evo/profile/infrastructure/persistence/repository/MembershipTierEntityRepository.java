package com.evo.profile.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.evo.profile.infrastructure.persistence.entity.MembershipTierEntity;

public interface MembershipTierEntityRepository extends JpaRepository<MembershipTierEntity, UUID> {
    Optional<MembershipTierEntity> findByDefaultTierTrue();

    @Query(
            "SELECT mt FROM MembershipTierEntity mt WHERE mt.minPoints > :currentMinPoints AND mt.deleted = false ORDER BY mt.minPoints ASC")
    List<MembershipTierEntity> findNextTierByMinPoints(@Param("currentMinPoints") Integer currentMinPoints);
}
