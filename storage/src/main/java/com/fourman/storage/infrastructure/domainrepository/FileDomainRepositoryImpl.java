package com.fourman.storage.infrastructure.domainrepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fourman.common.exception.ResponseException;
import com.fourman.common.repository.AbstractDomainRepository;
import com.fourman.storage.domain.File;
import com.fourman.storage.domain.FileHistory;
import com.fourman.storage.domain.exception.NotFoundError;
import com.fourman.storage.domain.query.SearchFileQuery;
import com.fourman.storage.domain.repository.FileDomainRepository;
import com.fourman.storage.infrastructure.persistence.entity.FileEntity;
import com.fourman.storage.infrastructure.persistence.entity.FileHistoryEntity;
import com.fourman.storage.infrastructure.persistence.mapper.FileEntityMapper;
import com.fourman.storage.infrastructure.persistence.mapper.FileHistoryEntityMapper;
import com.fourman.storage.infrastructure.persistence.repository.FileEntityRepository;
import com.fourman.storage.infrastructure.persistence.repository.FileHistoryEntityRepository;

@Repository
public class FileDomainRepositoryImpl extends AbstractDomainRepository<File, FileEntity, UUID>
        implements FileDomainRepository {
    private final FileEntityMapper fileEntityMapper;
    private final FileEntityRepository fileEntityRepository;
    private final FileHistoryEntityMapper fileHistoryEntityMapper;
    private final FileHistoryEntityRepository fileHistoryEntityRepository;

    public FileDomainRepositoryImpl(
            FileEntityRepository fileEntityRepository,
            FileEntityMapper fileEntityMapper,
            FileHistoryEntityMapper fileHistoryEntityMapper,
            FileHistoryEntityRepository fileHistoryEntityRepository) {
        super(fileEntityRepository, fileEntityMapper);
        this.fileEntityRepository = fileEntityRepository;
        this.fileEntityMapper = fileEntityMapper;
        this.fileHistoryEntityMapper = fileHistoryEntityMapper;
        this.fileHistoryEntityRepository = fileHistoryEntityRepository;
    }

    @Override
    public List<File> search(SearchFileQuery searchFileQuery) {
        List<FileEntity> fileEntities = fileEntityRepository.search(searchFileQuery);
        return fileEntityMapper.toDomainModelList(fileEntities);
    }

    @Override
    public Long count(SearchFileQuery searchFileQuery) {
        return fileEntityRepository.count(searchFileQuery);
    }

    @Override
    public List<File> getAll() {
        List<FileEntity> fileEntities = fileEntityRepository.findAll();
        return fileEntityMapper.toDomainModelList(fileEntities);
    }

    @Override
    public List<File> findTemporaryFilesCreatedBefore(Instant cutoffTime) {
        List<FileEntity> fileEntities = fileEntityRepository.findTemporaryFilesCreatedBefore(cutoffTime);
        return fileEntityMapper.toDomainModelList(fileEntities);
    }

    @Override
    public List<File> saveAll(List<File> domains) {
        List<FileHistory> fileHistories = domains.stream().map(File::getHistory).toList();
        List<FileEntity> fileEntities = fileEntityMapper.toEntityList(domains);
        List<FileHistoryEntity> fileHistoryEntities = fileHistoryEntityMapper.toEntityList(fileHistories);
        fileHistoryEntityRepository.saveAll(fileHistoryEntities);
        return fileEntityMapper.toDomainModelList(fileEntityRepository.saveAll(fileEntities));
    }

    @Override
    public File getById(UUID fileId) {
        FileEntity fileEntity = fileEntityRepository
                .findById(fileId)
                .orElseThrow(() -> new ResponseException(NotFoundError.FILE_NOT_FOUND));
        return fileEntityMapper.toDomainModel(fileEntity);
    }
}
