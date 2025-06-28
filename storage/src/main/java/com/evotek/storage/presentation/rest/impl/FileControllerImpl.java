package com.evotek.storage.presentation.rest.impl;

import java.util.List;
import java.util.UUID;

import com.evo.common.dto.response.PagingResponse;
import com.evotek.storage.presentation.rest.FileController;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.evo.common.dto.response.Response;
import com.evo.common.dto.response.FileResponse;
import com.evotek.storage.application.dto.request.SearchFileRequest;
import com.evotek.storage.application.dto.request.UpdateFileRequest;
import com.evotek.storage.application.service.FileCommandService;
import com.evotek.storage.application.service.FileQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileControllerImpl implements FileController {
    private final FileCommandService fileCommandService;
    private final FileQueryService fileQueryService;

    @Override
    public Response<List<FileResponse>> storeFile(
            @RequestPart List<MultipartFile> files, @RequestParam boolean isPublic, @RequestParam String description) {
        return Response.of(this.fileCommandService.storeFile(files, isPublic, description));
    }

    @Override
    public Response<FileResponse> storeOneFile(
            @RequestPart MultipartFile file, @RequestParam boolean isPublic, @RequestParam String description) {
        return Response.of(this.fileCommandService.storeOneFile(file, isPublic, description));
    }

    @Override
    public Response<FileResponse> getFile(@PathVariable UUID filedId) {
        return Response.of(this.fileQueryService.getPrivateFile(filedId));
    }

    @Override
    public PagingResponse<FileResponse> searchFiles(@RequestBody SearchFileRequest searchFileRequest) {
        return PagingResponse.of(this.fileQueryService.search(searchFileRequest));
    }

    @Override
    public Response<FileResponse> updateFile(@RequestBody UpdateFileRequest updateFileRequest) {
        return Response.of(this.fileCommandService.updateFile(updateFileRequest));
    }

    @Override
    public Response<Void> deleteFile(@PathVariable UUID fileId) {
        this.fileCommandService.deleteFile(fileId);
        return Response.ok();
    }
}
