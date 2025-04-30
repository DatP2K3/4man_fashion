package com.evo.product.domain;

import java.util.UUID;

import com.evo.common.Auditor;
import com.evo.product.domain.command.CreateTagDescriptionCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
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
}
