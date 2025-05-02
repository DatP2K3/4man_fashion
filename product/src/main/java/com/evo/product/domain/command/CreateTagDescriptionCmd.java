package com.evo.product.domain.command;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTagDescriptionCmd {
    private String name;
    private UUID categoryId;
}
