package com.evo.product.domain.command;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateOrUpdateProductImageCmd {
    private UUID id;
    private UUID productId;
    private UUID fileId;
    private Boolean avatar;
}
