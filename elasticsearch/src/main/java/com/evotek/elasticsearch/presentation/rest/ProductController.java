package com.evotek.elasticsearch.presentation.rest;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.PageApiResponse;
import com.evotek.elasticsearch.application.dto.request.SearchProductRequest;
import com.evotek.elasticsearch.application.dto.response.ProductDocumentDTO;
import com.evotek.elasticsearch.application.dto.response.SearchProductDTO;
import com.evotek.elasticsearch.application.service.ProductQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductQueryService productQueryService;

    @PostMapping("/products/search")
    PageApiResponse<List<ProductDocumentDTO>> searchProduct(@RequestBody SearchProductRequest request) {
        SearchProductDTO searchProductResponse = productQueryService.searchProduct(request);
        PageApiResponse.PageableResponse pageableResponse = PageApiResponse.PageableResponse.builder()
                .pageIndex(searchProductResponse.getPageIndex())
                .totalPages(searchProductResponse.getTotalPages())
                .totalElements(searchProductResponse.getTotalElements())
                .pageSize(searchProductResponse.getPageSize())
                .hasPrevious(searchProductResponse.isHasPrevious())
                .hasNext(searchProductResponse.isHasNext())
                .build();

        return PageApiResponse.<List<ProductDocumentDTO>>builder()
                .data(searchProductResponse.getProducts())
                .pageable(pageableResponse)
                .success(true)
                .code(200)
                .message("Search products successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
