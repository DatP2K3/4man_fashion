package com.evo.product.application.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateCategoryRequest {
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String productType;

    private String description;

    List<CreateTagDescriptionRequest> tagDescriptions;
}
