package com.evotek.storage.presentation.rest;

import com.evo.common.dto.response.Response;
import com.evo.common.dto.response.FileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "File Public API")
@RequestMapping("/api/public/file")
@Validated
public interface FilePublicController {

    @Operation(summary = "Get public file by ID")
    @GetMapping("/{filedId}")
    Response<FileResponse> getFile(@PathVariable UUID filedId);
}
