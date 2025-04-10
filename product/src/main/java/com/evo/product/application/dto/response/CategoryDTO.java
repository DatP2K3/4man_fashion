package com.evo.product.application.dto.response;

import com.evo.product.domain.TagDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CategoryDTO {
    private UUID id;
    private String name;
    private String productType;
    private String description;
    List<TagDescriptionDTO> tagDescriptions;
}
