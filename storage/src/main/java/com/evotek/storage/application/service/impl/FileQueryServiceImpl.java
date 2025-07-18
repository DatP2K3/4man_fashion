package com.evotek.storage.application.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.evo.common.dto.response.FileResponse;
import com.evo.common.dto.response.PageDTO;
import com.evotek.storage.application.dto.mapper.FileResponseMapper;
import com.evotek.storage.application.dto.request.SearchFileRequest;
import com.evotek.storage.application.mapper.QueryMapper;
import com.evotek.storage.application.service.FileQueryService;
import com.evotek.storage.domain.File;
import com.evotek.storage.domain.query.SearchFileQuery;
import com.evotek.storage.domain.repository.FileDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileQueryServiceImpl implements FileQueryService {
    private final FileDomainRepository fileDomainRepository;
    private final FileResponseMapper fileResponseMapper;
    private final QueryMapper queryMapper;

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
        String url = "http://localhost:8080/api/uploads/private/" + file.getMd5Name();
        FileResponse fileResponse = fileResponseMapper.domainModelToDTO(file);
        fileResponse.setUrl(url);
        return fileResponse;
    }

    @Override
    public FileResponse getPublicFile(UUID filedId) {
        File file = fileDomainRepository.getById(filedId);
        String url = "http://localhost:8080/api/uploads/public/" + file.getMd5Name();
        FileResponse fileResponse = fileResponseMapper.domainModelToDTO(file);
        fileResponse.setUrl(url);
        return fileResponse;
    }
}
