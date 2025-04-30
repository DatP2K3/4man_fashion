package com.evo.product.application.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDescriptionDTO {
    private UUID id;
    private String name;
    private UUID categoryId;
    private boolean deleted;
}
