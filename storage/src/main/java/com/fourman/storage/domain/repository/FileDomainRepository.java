package com.fourman.storage.domain.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fourman.common.repository.DomainRepository;
import com.fourman.storage.domain.File;
import com.fourman.storage.domain.query.SearchFileQuery;

public interface FileDomainRepository extends DomainRepository<File, UUID> {
    List<File> search(SearchFileQuery searchFileQuery);

    Long count(SearchFileQuery searchFileQuery);

    List<File> getAll();

    List<File> findTemporaryFilesCreatedBefore(Instant cutoffTime);
}
;
