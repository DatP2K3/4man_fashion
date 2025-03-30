package com.evo.product.domain.command;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateOrUpdateCategoryCmd {
    private UUID id;
    private String name;
    private String productType;
    private String description;
    List<CreateTagDescriptionCmd> tagDescriptions;
}
