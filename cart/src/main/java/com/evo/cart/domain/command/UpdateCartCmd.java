package com.evo.cart.domain.command;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateCartCmd {
    private UUID id;
    private UUID userId;
    private List<CreateCartItemCmd> cartItems;
}
