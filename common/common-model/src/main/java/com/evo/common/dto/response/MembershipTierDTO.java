package com.evo.common.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipTierDTO {
    private UUID id;
    private String name;
    private Double cashbackPercentage;
    private Integer minPoints;
    private boolean deleted;
    private boolean defaultTier;
}
