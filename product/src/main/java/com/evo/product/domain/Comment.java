package com.evo.product.domain;

import java.util.UUID;

import com.evo.common.Auditor;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Comment extends Auditor {
    private UUID id;
    private UUID productId;
    private UUID userId;
    private String content;
    private int rating;
}
