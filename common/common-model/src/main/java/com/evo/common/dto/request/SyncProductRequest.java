package com.evo.common.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncProductRequest {
    private UUID id;
    private String name;
    private Long originPrice;
    private UUID categoryId;
    private Long totalSold;
    private BigDecimal averageRating;
    private Boolean hidden;
    private UUID avatarId;
}
