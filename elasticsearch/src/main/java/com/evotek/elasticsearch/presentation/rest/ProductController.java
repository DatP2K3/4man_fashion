package com.evotek.elasticsearch.presentation.rest;

import com.evo.common.dto.response.Response;
import com.evo.common.dto.response.PagingResponse;
import com.evotek.elasticsearch.application.dto.request.SearchProductRequest;
import com.evotek.elasticsearch.application.dto.response.ProductDocumentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Product Search API")
@RequestMapping("/api")
@Validated
public interface ProductController {

    @Operation(summary = "Search products")
    @PostMapping("/products/search")
    PagingResponse<ProductDocumentDTO> searchProduct(@RequestBody SearchProductRequest request);

    @Operation(summary = "Autocomplete product names")
    @GetMapping("/products/autocomplete")
    Response<List<String>> autocompleteProductNames(
            @RequestParam String keyword, @RequestParam(defaultValue = "10") int limit);
}
