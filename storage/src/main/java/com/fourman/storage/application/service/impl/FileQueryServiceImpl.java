package com.fourman.storage.application.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fourman.common.dto.response.FileResponse;
import com.fourman.common.dto.response.PageDTO;
import com.fourman.storage.application.dto.mapper.FileResponseMapper;
import com.fourman.storage.application.dto.request.SearchFileRequest;
import com.fourman.storage.application.mapper.QueryMapper;
import com.fourman.storage.application.service.FileQueryService;
import com.fourman.storage.domain.File;
import com.fourman.storage.domain.query.SearchFileQuery;
import com.fourman.storage.domain.repository.FileDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileQueryServiceImpl implements FileQueryService {
    private final FileDomainRepository fileDomainRepository;
    private final FileResponseMapper fileResponseMapper;
    private final QueryMapper queryMapper;

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
}
