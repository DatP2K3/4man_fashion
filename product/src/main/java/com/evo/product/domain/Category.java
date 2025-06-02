package com.evo.product.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.evo.common.Auditor;
import com.evo.product.domain.command.CreateOrUpdateCategoryCmd;
import com.evo.product.domain.command.CreateTagDescriptionCmd;
import com.evo.product.infrastructure.support.IdUtils;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Category extends Auditor {
    private UUID id;
    private String name;
    private String productType;
    private String description;
    List<TagDescription> tagDescriptions;
    private Boolean deleted;

    public Category(CreateOrUpdateCategoryCmd cmd) {
        this.id = IdUtils.nextId();
        this.name = cmd.getName();
        this.productType = cmd.getProductType();
        this.description = cmd.getDescription();
        if (cmd.getTagDescriptions() != null) {
            createOrUpdateTagDescription(cmd.getTagDescriptions());
        }
    }

    private void createOrUpdateTagDescription(List<CreateTagDescriptionCmd> tagDescriptionCmds) {
        if (this.tagDescriptions == null) {
            this.tagDescriptions = new ArrayList<>();
        }

        Map<String, TagDescription> tagDescriptionMap = this.tagDescriptions.stream()
                .peek(rp -> rp.setDeleted(true))
                .collect(Collectors.toMap(TagDescription::getName, t -> t));

        for (CreateTagDescriptionCmd tagDescriptionCmd : tagDescriptionCmds) {
            String name = tagDescriptionCmd.getName();
            if (tagDescriptionMap.containsKey(name)) {
                tagDescriptionMap.get(name).setDeleted(false);
            } else {
                tagDescriptionCmd.setCategoryId(this.id);
                TagDescription tagDescription = new TagDescription(tagDescriptionCmd);
                this.tagDescriptions.add(tagDescription);
            }
        }
    }

    public void update(CreateOrUpdateCategoryCmd cmd) {
        this.name = cmd.getName();
        this.productType = cmd.getProductType();
        this.description = cmd.getDescription();
        if (cmd.getTagDescriptions() != null) {
            createOrUpdateTagDescription(cmd.getTagDescriptions());
        }
    }

    public void toggleVisibility() {
        if (this.deleted == null) {
            this.deleted = false;
        } else {
            this.deleted = !this.deleted;
        }
        if (this.tagDescriptions != null) {
            if (this.deleted) {
                this.tagDescriptions.forEach(tag -> tag.setDeleted(true));
            } else {
                this.tagDescriptions.forEach(tag -> tag.setDeleted(false));
            }
        }
    }
}
