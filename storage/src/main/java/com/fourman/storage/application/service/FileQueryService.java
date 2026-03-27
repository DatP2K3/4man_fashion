package com.fourman.storage.application.service;

import java.util.UUID;

import com.fourman.common.dto.response.FileResponse;
import com.fourman.common.dto.response.PageDTO;
import com.fourman.storage.application.dto.request.SearchFileRequest;

public interface FileQueryService {
    PageDTO<FileResponse> search(SearchFileRequest searchFileRequest);

    FileResponse getPrivateFile(UUID filedId);

    FileResponse getPublicFile(UUID filedId);
}
