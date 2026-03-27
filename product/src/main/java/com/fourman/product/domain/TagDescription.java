package com.fourman.product.domain;

import java.util.UUID;

import com.fourman.common.Auditor;
import com.fourman.product.domain.command.CreateTagDescriptionCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class TagDescription extends Auditor {
    private UUID id;
    private String name;
    private UUID categoryId;
    private boolean deleted;

    public TagDescription(CreateTagDescriptionCmd cmd) {
        this.name = cmd.getName();
        this.categoryId = cmd.getCategoryId();
        this.deleted = false;
    }

    public void markAsDeleted() {
        this.deleted = true;
    }

    public void restore() {
        this.deleted = false;
    }
}
