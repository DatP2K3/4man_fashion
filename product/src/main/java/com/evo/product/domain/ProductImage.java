package com.evo.product.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class ProductImage {
    private UUID id;
    private UUID productId;
    private UUID fileId;
    private String thumbnail;
}
