package com.evo.common.dto.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantEvent {
    List<ProductVariantSync> productVariantSyncs;
}
