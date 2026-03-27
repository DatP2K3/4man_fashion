package com.fourman.storage.domain;

import java.util.UUID;

import com.fourman.common.Auditor;
import com.fourman.storage.domain.command.WriteHistoryCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class FileHistory extends Auditor {
    private UUID fileId;
    private String action;

    public FileHistory(WriteHistoryCmd cmd) {
        this.fileId = cmd.getFileId();
        this.action = cmd.getAction();
    }
}
