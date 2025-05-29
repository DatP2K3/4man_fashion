package com.evotek.elasticsearch.application.service;

import com.evotek.elasticsearch.application.dto.request.SearchProductRequest;
import com.evotek.elasticsearch.application.dto.response.SearchProductDTO;

import java.util.List;

public interface ProductQueryService {
    SearchProductDTO searchProduct(SearchProductRequest request);

    List<String> autocompleteProductNames(String keyword, int limit);
}
