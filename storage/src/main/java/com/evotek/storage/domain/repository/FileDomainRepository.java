package com.evotek.storage.domain.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.evo.common.repository.DomainRepository;
import com.evotek.storage.domain.File;
import com.evotek.storage.domain.query.SearchFileQuery;

public interface FileDomainRepository extends DomainRepository<File, UUID> {
    List<File> search(SearchFileQuery searchFileQuery);

    Long count(SearchFileQuery searchFileQuery);

    List<File> getAll();

    List<File> findTemporaryFilesCreatedBefore(Instant cutoffTime);
};
