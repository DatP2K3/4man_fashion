package com.evo.product.domain.command;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrUpdateProductImageCmd {
    private UUID id;
    private UUID productId;
    private UUID fileId;
    private Boolean avatar;
    private Boolean deleted;
}
