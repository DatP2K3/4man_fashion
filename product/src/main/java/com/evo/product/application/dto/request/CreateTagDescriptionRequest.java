package com.evo.product.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTagDescriptionRequest {
    private String name;
    private UUID categoryId;
}
