package com.evotek.storage.presentation.rest.impl;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evo.common.dto.response.FileResponse;
import com.evo.common.dto.response.Response;
import com.evotek.storage.application.service.FileQueryService;
import com.evotek.storage.presentation.rest.FilePublicController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/public/file")
@RequiredArgsConstructor
public class FilePublicControllerImpl implements FilePublicController {
    private final FileQueryService fileQueryService;

    @Override
    public Response<FileResponse> getFile(@PathVariable UUID filedId) {
        return Response.of(this.fileQueryService.getPublicFile(filedId));
    }
}
