package com.evotek.elasticsearch.application.service.impl;

import com.evotek.elasticsearch.application.dto.request.SearchProductRequest;
import com.evotek.elasticsearch.application.dto.response.SearchProductDTO;

public interface ProductQueryService {
    SearchProductDTO searchProduct(SearchProductRequest request);
}
