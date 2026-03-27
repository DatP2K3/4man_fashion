package com.fourman.common.dto.event;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent {
    List<ProductSync> productSyncs;
}
