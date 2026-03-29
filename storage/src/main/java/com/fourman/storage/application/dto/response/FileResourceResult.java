package com.fourman.storage.application.dto.response;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileResourceResult {
    private final Resource resource;
    private final String contentType;
    private final String originalFileName;
}
