package com.fourman.storage.presentation.rest.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fourman.common.dto.response.FileResponse;
import com.fourman.common.dto.response.PagingResponse;
import com.fourman.common.dto.response.Response;
import com.fourman.storage.application.dto.request.SearchFileRequest;
import com.fourman.storage.application.dto.request.UpdateFileRequest;
import com.fourman.storage.application.dto.response.FileResourceResult;
import com.fourman.storage.application.service.FileCommandService;
import com.fourman.storage.application.service.FileQueryService;
import com.fourman.storage.presentation.rest.FileController;

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
    public ResponseEntity<Resource> downloadPrivateFile(@PathVariable UUID fileId) {
        FileResourceResult result = fileQueryService.getPrivateFileResource(fileId);
        String contentType = result.getContentType() != null ? result.getContentType() : "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + result.getOriginalFileName() + "\"")
                .body(result.getResource());
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
