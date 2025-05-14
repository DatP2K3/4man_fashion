package com.evo.product.domain;

import java.time.Instant;
import java.util.UUID;

import com.evo.common.Auditor;
import com.evo.common.enums.DiscountStatus;
import com.evo.common.enums.DiscountType;
import com.evo.product.domain.command.CreateOrUpdateDiscountCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Discount extends Auditor {
    private UUID id;
    private String name;
    private UUID productId;
    private Instant startDate;
    private Instant endDate;
    private DiscountStatus status;
    private DiscountType discountType;
    private Integer discountPercentage;
    private Long discountPrice;

    public Discount(CreateOrUpdateDiscountCmd createOrUpdateDiscountCmd) {
        this.name = createOrUpdateDiscountCmd.getName();
        this.productId = createOrUpdateDiscountCmd.getProductId();
        this.startDate = createOrUpdateDiscountCmd.getStartDate();
        this.endDate = createOrUpdateDiscountCmd.getEndDate();
        Instant now = Instant.now();
        if (this.startDate.isAfter(now)) {
            this.status = DiscountStatus.SCHEDULED;
        } else if (this.endDate.isBefore(now)) {
            this.status = DiscountStatus.EXPIRED;
        } else if (this.startDate.isAfter(now) && now.isBefore(this.endDate)) {
            this.status = DiscountStatus.ACTIVE;
        }
        this.discountType = createOrUpdateDiscountCmd.getDiscountType();
        this.discountPercentage = createOrUpdateDiscountCmd.getDiscountPercentage();
        this.discountPrice = createOrUpdateDiscountCmd.getDiscountPrice();
    }
}
