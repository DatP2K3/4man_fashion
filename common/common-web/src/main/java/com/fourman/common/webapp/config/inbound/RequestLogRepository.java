package com.fourman.common.webapp.config.inbound;

import java.time.Instant;
import java.util.UUID;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, UUID> {

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    long deleteByRequestAtBefore(Instant threshold);
}
