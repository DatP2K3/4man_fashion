package com.fourman.elasticsearch.application.service;

import java.util.UUID;

import com.fourman.elasticsearch.domain.command.SyncProductCmd;

public interface ProductCommandService {
    void create(SyncProductCmd syncProductCmd);

    void update(SyncProductCmd syncProductCmd);

    void delete(UUID selfUserID);
}
