package com.evotek.storage.presentation.rest;

import com.evo.common.dto.response.PagingResponse;
import com.evo.common.dto.response.Response;
import com.evo.common.dto.response.FileResponse;
import com.evotek.storage.application.dto.request.SearchFileRequest;
import com.evotek.storage.application.dto.request.UpdateFileRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(name = "File API")
@RequestMapping("/api")
@Validated
public interface FileController {

<<<<<<< HEAD
=======
    @Operation(summary = "Upload multiple files")
>>>>>>> keycloak_security
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/file/upload")
    Response<List<FileResponse>> storeFile(
            @RequestPart List<MultipartFile> files, @RequestParam boolean isPublic, @RequestParam String description);

<<<<<<< HEAD
=======
    @Operation(summary = "Upload single file")
>>>>>>> keycloak_security
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/file/upload-only-one")
    Response<FileResponse> storeOneFile(
            @RequestPart MultipartFile file, @RequestParam boolean isPublic, @RequestParam String description);

    @Operation(summary = "Get file by ID")
    @PreAuthorize("hasPermission(null, 'file.read')")
    @GetMapping("/file/{filedId}")
    Response<FileResponse> getFile(@PathVariable UUID filedId);

    @Operation(summary = "Search files")
    @PreAuthorize("hasPermission(null, 'file.admin')")
    @GetMapping("/file")
    PagingResponse<FileResponse> searchFiles(@RequestBody SearchFileRequest searchFileRequest);

<<<<<<< HEAD
=======
    @Operation(summary = "Update file")
>>>>>>> keycloak_security
    @PreAuthorize("hasRole('USER')")
    @PutMapping("file/update")
    Response<FileResponse> updateFile(@RequestBody UpdateFileRequest updateFileRequest);

<<<<<<< HEAD
=======
    @Operation(summary = "Delete file")
>>>>>>> keycloak_security
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("file/delete/{fileId}")
    Response<Void> deleteFile(@PathVariable UUID fileId);
}

// TODO: Chuyển đổi get private file không trả về url nữa mà gender ảnh luôn//tham khảo Quân
