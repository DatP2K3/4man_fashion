package com.evotek.elasticsearch.application.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductRequest {
    private String keyword;
    private UUID categoryId;
    private Boolean hidden;
    private int page;
    private int size;
    private String sortField;
    private String sortDirection;
    private Integer minExperience;
    private Integer maxExperience;
}
