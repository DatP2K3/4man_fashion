package com.evo.common.dto.event;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.evo.common.enums.DiscountType;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent {
    List<ProductSync> productSyncs;
}
