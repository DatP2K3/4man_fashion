package com.fourman.elasticsearch.application.dto.request;

import java.util.UUID;

import com.fourman.common.dto.request.PagingRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductRequest extends PagingRequest {
    private String keyword;
    private UUID categoryId;
    private Boolean hidden;
    private String sortDirection;
}
