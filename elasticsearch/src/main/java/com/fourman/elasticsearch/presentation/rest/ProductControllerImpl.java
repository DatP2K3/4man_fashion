package com.fourman.elasticsearch.presentation.rest;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.fourman.common.dto.response.PagingResponse;
import com.fourman.common.dto.response.Response;
import com.fourman.elasticsearch.application.dto.request.SearchProductRequest;
import com.fourman.elasticsearch.application.dto.response.ProductDocumentDTO;
import com.fourman.elasticsearch.application.service.ProductQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductQueryService productQueryService;

    @Override
    public PagingResponse<ProductDocumentDTO> searchProduct(@RequestBody SearchProductRequest request) {
        return PagingResponse.of(this.productQueryService.searchProduct(request));
    }

    @Override
    public Response<List<String>> autocompleteProductNames(
            @RequestParam String keyword, @RequestParam(defaultValue = "10") int limit) {
        return Response.of(this.productQueryService.autocompleteProductNames(keyword, limit));
    }
}
