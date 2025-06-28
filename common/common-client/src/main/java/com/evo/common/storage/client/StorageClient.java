package com.evo.common.storage.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.evo.common.dto.request.SearchFileRequest;
import com.evo.common.dto.request.UpdateFileRequest;
import com.evo.common.dto.response.Response;
import com.evo.common.dto.response.FileResponse;
import com.evo.common.dto.response.PagingResponse;
import com.evo.common.storage.config.FeignStorageClientConfiguration;

@FeignClient(
        name = "storage-service",
        url = "${storage-service.url:}",
        contextId = "common-storage-with-token",
        configuration = FeignStorageClientConfiguration.class,
        fallbackFactory = StorageClientFallback.class)
public interface StorageClient {
    @PostMapping(value = "/api/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response<List<FileResponse>> uploadFiles(
            @RequestPart("files") List<MultipartFile> files,
            @RequestParam("isPublic") boolean isPublic,
            @RequestParam("description") String description);

    @PutMapping(value = "/api/file/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Response<FileResponse> updateFile(@RequestBody UpdateFileRequest updateFileRequest);

    @DeleteMapping("/api/file/delete/{fileId}")
    Response<Void> deleteFile(@PathVariable("fileId") UUID fileId);

    @GetMapping("/api/file/{fileId}")
    Response<FileResponse> getFile(@PathVariable("fileId") UUID fileId);

    @PostMapping(value = "/api/file", consumes = MediaType.APPLICATION_JSON_VALUE)
    PagingResponse<FileResponse> searchFiles(@RequestBody SearchFileRequest searchFileRequest);

    @GetMapping("/api/file/test-retry")
    Response<Void> testRetry();
}
