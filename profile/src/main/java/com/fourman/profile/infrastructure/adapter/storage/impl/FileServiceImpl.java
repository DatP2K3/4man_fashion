package com.fourman.profile.infrastructure.adapter.storage.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fourman.common.dto.response.FileResponse;
import com.fourman.common.storage.client.StorageClient;
import com.fourman.profile.infrastructure.adapter.storage.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final StorageClient storageClient;

    @Override
    public List<FileResponse> uploadFile(List<MultipartFile> files, boolean isPublic, String description) {
        return storageClient.uploadFiles(files, isPublic, description).getData();
    }

    @Override
    public FileResponse getFile(UUID fileId) {
        return storageClient.getFile(fileId).getData();
    }

    @Override
    public void deleteFile(UUID fileId) {
        storageClient.deleteFile(fileId);
    }
}
