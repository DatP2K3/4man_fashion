package com.evo.product.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateCategoryRequest {
    private UUID id;
    private String name;
    private String productType;
    private String description;
    List<CreateTagDescriptionRequest> tagDescriptions;
}
