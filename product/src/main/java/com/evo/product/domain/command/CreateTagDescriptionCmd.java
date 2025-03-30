package com.evo.product.domain.command;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateTagDescriptionCmd {
    private String name;
    private UUID categoryId;
}
