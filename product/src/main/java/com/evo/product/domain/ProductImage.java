package com.evo.product.domain;

import java.util.UUID;

import com.evo.common.Auditor;
import com.evo.product.domain.command.CreateOrUpdateProductImageCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
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
}
