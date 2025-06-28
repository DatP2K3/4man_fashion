package com.evo.product.application.dto.request;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
