package com.fourman.elasticsearch.application.service;

import java.util.List;

import com.fourman.common.dto.response.PageDTO;
import com.fourman.elasticsearch.application.dto.request.SearchProductRequest;
import com.fourman.elasticsearch.application.dto.response.ProductDocumentDTO;

public interface ProductQueryService {
    PageDTO<ProductDocumentDTO> searchProduct(SearchProductRequest request);

    List<String> autocompleteProductNames(String keyword, int limit);
}
