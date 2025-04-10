package com.evo.product.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateProductImageRequest {
    private UUID id;
    private UUID productId;
    private UUID fileId;
    private Boolean avatar;
}
