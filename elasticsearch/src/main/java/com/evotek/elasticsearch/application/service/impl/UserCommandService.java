package com.evotek.elasticsearch.application.service.impl;

import java.util.UUID;

import com.evotek.elasticsearch.domain.command.SyncUserCmd;

public interface UserCommandService {
    void create(SyncUserCmd syncUserCmd);

    void update(SyncUserCmd syncUserCmd);

    void delete(UUID selfUserID);
}
