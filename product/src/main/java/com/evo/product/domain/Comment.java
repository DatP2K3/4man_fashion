package com.evo.product.domain;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Comment {
    private UUID id;
    private UUID productId;
    private UUID userId;
    private String content;
    private int rating;
}
