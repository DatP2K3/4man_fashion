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
        } else {
            this.status = DiscountStatus.ACTIVE;
        }
        this.discountType = createOrUpdateDiscountCmd.getDiscountType();
        this.discountPercentage = createOrUpdateDiscountCmd.getDiscountPercentage();
        this.discountPrice = createOrUpdateDiscountCmd.getDiscountPrice();
    }

    /**
     * Update discount details from command, recalculating status based on dates.
     */
    public void updateFrom(CreateOrUpdateDiscountCmd cmd) {
        if (cmd.getName() != null) {
            this.name = cmd.getName();
        }
        if (cmd.getDiscountPrice() != null) {
            this.discountPrice = cmd.getDiscountPrice();
        }
        if (cmd.getDiscountPercentage() != null) {
            this.discountPercentage = cmd.getDiscountPercentage();
        }
        if (cmd.getStartDate() != null) {
            this.startDate = cmd.getStartDate();
        }
        if (cmd.getEndDate() != null) {
            this.endDate = cmd.getEndDate();
        }
        recalculateStatus();
    }

    /**
     * Recalculate discount status based on current time and date range.
     */
    private void recalculateStatus() {
        Instant now = Instant.now();
        if (this.endDate.isBefore(now)) {
            this.status = DiscountStatus.EXPIRED;
        } else if (this.startDate.isAfter(now)) {
            this.status = DiscountStatus.SCHEDULED;
        } else {
            this.status = DiscountStatus.ACTIVE;
        }
    }

    /**
     * Activate this discount.
     */
    public void activate() {
        this.status = DiscountStatus.ACTIVE;
    }

    /**
     * Mark this discount as expired.
     */
    public void expire() {
        this.status = DiscountStatus.EXPIRED;
    }
}
