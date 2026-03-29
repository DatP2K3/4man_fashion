package com.fourman.storage.presentation.rest;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fourman.common.dto.response.FileResponse;
import com.fourman.common.dto.response.PagingResponse;
import com.fourman.common.dto.response.Response;
import com.fourman.storage.application.dto.request.SearchFileRequest;
import com.fourman.storage.application.dto.request.UpdateFileRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "File API")
@RequestMapping("/api")
@Validated
public interface FileController {

    @Operation(summary = "Upload multiple files")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/file/upload")
    Response<List<FileResponse>> storeFile(
            @RequestPart List<MultipartFile> files, @RequestParam boolean isPublic, @RequestParam String description);

    @Operation(summary = "Upload single file")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/file/upload-only-one")
    Response<FileResponse> storeOneFile(
            @RequestPart MultipartFile file, @RequestParam boolean isPublic, @RequestParam String description);

    @Operation(summary = "Get file metadata by ID")
    @PreAuthorize("hasPermission(null, 'file.read')")
    @GetMapping("/file/{filedId}")
    Response<FileResponse> getFile(@PathVariable UUID filedId);

    @Operation(summary = "Get private file content (returns image/file bytes directly)")
    @PreAuthorize("hasPermission(null, 'file.read')")
    @GetMapping("/file/{fileId}/download")
    ResponseEntity<org.springframework.core.io.Resource> downloadPrivateFile(@PathVariable UUID fileId);

    @Operation(summary = "Search files")
    @PreAuthorize("hasPermission(null, 'file.admin')")
    @GetMapping("/file")
    PagingResponse<FileResponse> searchFiles(@Valid @RequestBody SearchFileRequest searchFileRequest);

    @Operation(summary = "Update file")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("file/update")
    Response<FileResponse> updateFile(@Valid @RequestBody UpdateFileRequest updateFileRequest);

    @Operation(summary = "Delete file")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("file/delete/{fileId}")
    Response<Void> deleteFile(@PathVariable UUID fileId);
}
