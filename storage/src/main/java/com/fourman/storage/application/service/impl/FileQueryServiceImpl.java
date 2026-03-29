package com.fourman.storage.application.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.fourman.common.dto.response.FileResponse;
import com.fourman.common.dto.response.PageDTO;
import com.fourman.common.exception.ResponseException;
import com.fourman.storage.application.config.FileStorageProperties;
import com.fourman.storage.application.dto.mapper.FileResponseMapper;
import com.fourman.storage.application.dto.request.SearchFileRequest;
import com.fourman.storage.application.dto.response.FileResourceResult;
import com.fourman.storage.application.mapper.QueryMapper;
import com.fourman.storage.application.service.FileQueryService;
import com.fourman.storage.domain.File;
import com.fourman.storage.domain.exception.NotFoundError;
import com.fourman.storage.domain.query.SearchFileQuery;
import com.fourman.storage.domain.repository.FileDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileQueryServiceImpl implements FileQueryService {
    private final FileDomainRepository fileDomainRepository;
    private final FileResponseMapper fileResponseMapper;
    private final QueryMapper queryMapper;
    private final FileStorageProperties fileStorageProperties;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public PageDTO<FileResponse> search(SearchFileRequest request) {
        SearchFileQuery searchFileQuery = queryMapper.from(request);
        Long totalFiles = fileDomainRepository.count(searchFileQuery);
        if (totalFiles == 0) {
            return PageDTO.empty();
        }
        List<File> files = fileDomainRepository.search(searchFileQuery);
        List<FileResponse> fileResponses =
                files.stream().map(fileResponseMapper::domainModelToDTO).toList();
        return PageDTO.of(fileResponses, searchFileQuery.getPageIndex(), searchFileQuery.getPageSize(), totalFiles);
    }

    @Override
    public FileResponse getPrivateFile(UUID filedId) {
        File file = fileDomainRepository.getById(filedId);
        String url = baseUrl + "/api/uploads/private/" + file.getMd5Name();
        FileResponse fileResponse = fileResponseMapper.domainModelToDTO(file);
        fileResponse.setUrl(url);
        return fileResponse;
    }

    @Override
    public FileResponse getPublicFile(UUID filedId) {
        File file = fileDomainRepository.getById(filedId);
        String url = baseUrl + "/api/uploads/public/" + file.getMd5Name();
        FileResponse fileResponse = fileResponseMapper.domainModelToDTO(file);
        fileResponse.setUrl(url);
        return fileResponse;
    }

    @Override
    public FileResourceResult getPrivateFileResource(UUID fileId) {
        File file = fileDomainRepository.getById(fileId);
        try {
            Path filePath = Paths.get(fileStorageProperties.getPrivateUploadDir())
                    .toAbsolutePath()
                    .normalize()
                    .resolve(file.getMd5Name());
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResponseException(NotFoundError.FILE_NOT_FOUND);
            }
            return new FileResourceResult(resource, file.getFileType(), file.getOriginName());
        } catch (ResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseException(NotFoundError.FILE_NOT_FOUND);
        }
    }
}
