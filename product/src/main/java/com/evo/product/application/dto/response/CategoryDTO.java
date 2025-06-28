package com.evo.product.application.dto.response;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private UUID id;
    private String name;
    private String productType;
    private String description;
    private List<TagDescriptionDTO> tagDescriptions;
    private Boolean deleted;
}
