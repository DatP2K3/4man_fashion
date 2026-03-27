package com.fourman.product.domain;

import java.util.UUID;

import com.fourman.common.Auditor;
import com.fourman.product.domain.command.CreateOrUpdateProductImageCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class ProductImage extends Auditor {
    private UUID id;
    private UUID productId;
    private UUID fileId;
    private Boolean avatar;
    private Boolean deleted;

    public ProductImage(CreateOrUpdateProductImageCmd cmd) {
        if (cmd.getProductId() != null) {
            this.id = cmd.getId();
        }
        this.productId = cmd.getProductId();
        this.fileId = cmd.getFileId();
        this.avatar = cmd.getAvatar();
        this.deleted = false;
    }

    public void markAsDeleted() {
        this.deleted = true;
    }

    public void restore() {
        this.deleted = false;
    }
}
