package com.fourman.storage.application.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fourman.storage.application.service.FileCommandService;
import com.fourman.storage.domain.File;
import com.fourman.storage.domain.repository.FileDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CronJobService {
    private final FileDomainRepository fileDomainRepository;
    private final FileCommandService fileCommandService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupTemporaryFiles() {
        Instant cutoffTime = Instant.now().minusSeconds(24 * 60 * 60);

        List<File> unusedFiles = fileDomainRepository.findTemporaryFilesCreatedBefore(cutoffTime);

        for (File file : unusedFiles) {
            fileCommandService.deleteFile(file.getId());
        }
    }
}
