package com.evotek.elasticsearch.application.service;

import java.util.UUID;

import com.evotek.elasticsearch.domain.command.SyncProductCmd;

public interface ProductCommandService {
    void create(SyncProductCmd syncProductCmd);

    void update(SyncProductCmd syncProductCmd);

    void delete(UUID selfUserID);
}
