package com.evotek.elasticsearch.presentation.rest;

import java.util.List;

import com.evotek.elasticsearch.domain.ProductDocument;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evo.common.dto.response.PageApiResponse;
import com.evotek.elasticsearch.application.dto.request.SearchProductRequest;
import com.evotek.elasticsearch.application.dto.response.SearchProductDTO;
import com.evotek.elasticsearch.application.service.impl.ProductQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductQueryService productQueryService;

    @PostMapping("/products/search")
    PageApiResponse<List<ProductDocument>> searchUser(@RequestBody SearchProductRequest request) {
        SearchProductDTO searchUserResponse = productQueryService.searchProduct(request);
        PageApiResponse.PageableResponse pageableResponse = PageApiResponse.PageableResponse.builder()
                .pageIndex(searchUserResponse.getPageIndex())
                .totalPages(searchUserResponse.getTotalPages())
                .totalElements(searchUserResponse.getTotalElements())
                .pageSize(searchUserResponse.getPageSize())
                .hasPrevious(searchUserResponse.isHasPrevious())
                .hasNext(searchUserResponse.isHasNext())
                .build();

        return PageApiResponse.<List<ProductDocument>>builder()
                .data(searchUserResponse.getUsers())
                .pageable(pageableResponse)
                .success(true)
                .code(200)
                .message("Search products successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
