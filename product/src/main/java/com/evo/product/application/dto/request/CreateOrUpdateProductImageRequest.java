package com.evo.product.application.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateProductImageRequest {
    private UUID id;
    private UUID productId;
    private UUID fileId;
    private Boolean avatar;
    private Boolean deleted;
}
