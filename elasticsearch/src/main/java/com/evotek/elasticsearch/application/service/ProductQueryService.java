package com.evotek.elasticsearch.application.service;

import java.util.List;

import com.evo.common.dto.response.PageDTO;
import com.evotek.elasticsearch.application.dto.request.SearchProductRequest;
import com.evotek.elasticsearch.application.dto.response.ProductDocumentDTO;

public interface ProductQueryService {
    PageDTO<ProductDocumentDTO> searchProduct(SearchProductRequest request);

    List<String> autocompleteProductNames(String keyword, int limit);
}
