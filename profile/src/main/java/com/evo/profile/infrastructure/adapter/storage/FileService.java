package com.evo.profile.infrastructure.adapter.storage;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.evo.common.dto.response.FileResponse;

public interface FileService {
    List<FileResponse> uploadFile(List<MultipartFile> files, boolean isPublic, String description);

    FileResponse getFile(UUID fileId);

    void deleteFile(UUID fileId);
}
