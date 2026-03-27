package com.fourman.storage.presentation.rest;

import java.util.UUID;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourman.common.dto.response.FileResponse;
import com.fourman.common.dto.response.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "File Public API")
@RequestMapping("/api/public/file")
@Validated
public interface FilePublicController {

    @Operation(summary = "Get public file by ID")
    @GetMapping("/{filedId}")
    Response<FileResponse> getFile(@PathVariable UUID filedId);
}
