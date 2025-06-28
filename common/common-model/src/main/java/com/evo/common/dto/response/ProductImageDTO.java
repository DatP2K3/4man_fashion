package com.evo.common.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO {
    private UUID id;
    private UUID productId;
    private UUID fileId;
    private Boolean avatar;
    private Boolean deleted;
}
