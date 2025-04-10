package com.evo.product.domain;

import com.evo.product.domain.command.CreateOrUpdateProductImageCmd;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class ProductImage {
    private UUID id;
    private UUID productId;
    private UUID fileId;
    private Boolean  avatar;

    public ProductImage(CreateOrUpdateProductImageCmd cmd) {
        this.id = cmd.getId();
        this.productId = cmd.getProductId();
        this.fileId = cmd.getFileId();
        this.avatar = cmd.getAvatar() ;
    }
}
