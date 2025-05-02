package com.evotek.elasticsearch.domain;

import java.math.BigDecimal;
import java.util.UUID;

import com.evo.common.enums.DiscountType;
import com.evotek.elasticsearch.domain.command.SyncProductCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class ProductDocument {
    private UUID id;
    private String name;
    private Long originPrice;
    private Long discountPrice;
    private Integer discountPercentage;
    private DiscountType discountType;
    private UUID categoryId;
    private Long totalSold;
    private BigDecimal averageRating;
    private Boolean hidden;
    private UUID avatarId;

    public ProductDocument(SyncProductCmd syncProductCmd) {
        this.id = syncProductCmd.getId();
        this.name = syncProductCmd.getName();
        this.originPrice = syncProductCmd.getOriginPrice();
        this.categoryId = syncProductCmd.getCategoryId();
        this.totalSold = syncProductCmd.getTotalSold();
        this.discountPrice = syncProductCmd.getDiscountPrice();
        this.discountPercentage = syncProductCmd.getDiscountPercent();
        this.discountType = syncProductCmd.getDiscountType();
        this.averageRating = syncProductCmd.getAverageRating();
        this.hidden = syncProductCmd.getHidden();
        this.avatarId = syncProductCmd.getAvatarId();
    }

    public void update(SyncProductCmd syncProductCmd) {
        this.name = syncProductCmd.getName();
        this.originPrice = syncProductCmd.getOriginPrice();
        this.categoryId = syncProductCmd.getCategoryId();
        this.totalSold = syncProductCmd.getTotalSold();
        this.discountPrice = syncProductCmd.getDiscountPrice();
        this.discountPercentage = syncProductCmd.getDiscountPercent();
        this.discountType = syncProductCmd.getDiscountType();
        this.averageRating = syncProductCmd.getAverageRating();
        this.hidden = syncProductCmd.getHidden();
        this.avatarId = syncProductCmd.getAvatarId();
    }
}
