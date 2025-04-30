package com.evotek.elasticsearch.domain.command;

import java.math.BigDecimal;
import java.util.UUID;

import com.evo.common.enums.DiscountType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SyncProductCmd {
    private UUID id;
    private String name;
    private Long originPrice;
    private Long discountPrice;
    private Integer discountPercent;
    private DiscountType discountType;
    private UUID categoryId;
    private Long totalSold;
    private BigDecimal averageRating;
    private Boolean hidden;
    private UUID avatarId;
}
