package com.evotek.elasticsearch.application.dto.request;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserRequest {
    private String keyword;
    private UUID roleId;
    private Boolean locked;
    private int page;
    private int size;
    private String sortField;
    private String sortDirection;
    private Integer minExperience;
    private Integer maxExperience;
}
