package com.fourman.storage.application.service;

import java.util.UUID;

import com.fourman.common.dto.response.FileResponse;
import com.fourman.common.dto.response.PageDTO;
import com.fourman.storage.application.dto.request.SearchFileRequest;
import com.fourman.storage.application.dto.response.FileResourceResult;

public interface FileQueryService {
    PageDTO<FileResponse> search(SearchFileRequest searchFileRequest);

    FileResponse getPrivateFile(UUID filedId);

    FileResponse getPublicFile(UUID filedId);

    FileResourceResult getPrivateFileResource(UUID fileId);
}
