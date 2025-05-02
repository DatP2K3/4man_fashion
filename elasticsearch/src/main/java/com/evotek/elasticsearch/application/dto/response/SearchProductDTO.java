package com.evotek.elasticsearch.application.dto.response;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchProductDTO {
    List<ProductDocumentDTO> products;
    private int pageIndex;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;
    private boolean hasPrevious;
}
