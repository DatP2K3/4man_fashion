package com.evotek.elasticsearch.domain.repository;

import java.util.UUID;

import com.evotek.elasticsearch.domain.User;
import com.evotek.elasticsearch.infrastructure.domainrepository.DocumentDomainRepository;

public interface UserDomainRepository extends DocumentDomainRepository<User, UUID> {
    void deleteById(UUID userId);
}
