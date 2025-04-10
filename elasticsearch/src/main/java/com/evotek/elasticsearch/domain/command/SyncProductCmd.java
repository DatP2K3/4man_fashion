package com.evotek.elasticsearch.domain.command;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SyncProductCmd {
    private UUID id;
    private String name;
    private Long originPrice;
    private UUID categoryId;
    private Long totalSold;
    private BigDecimal averageRating;
    private Boolean hidden;
    private UUID avatarId;
}
