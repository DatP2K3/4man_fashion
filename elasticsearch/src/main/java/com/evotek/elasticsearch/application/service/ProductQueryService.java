package com.evotek.elasticsearch.application.service;

import java.util.List;

import com.evotek.elasticsearch.application.dto.request.SearchProductRequest;
import com.evotek.elasticsearch.application.dto.response.SearchProductDTO;

public interface ProductQueryService {
    SearchProductDTO searchProduct(SearchProductRequest request);

    List<String> autocompleteProductNames(String keyword, int limit);
}
