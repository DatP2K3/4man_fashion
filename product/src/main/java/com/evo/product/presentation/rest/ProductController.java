package com.evo.product.presentation.rest;

import com.evo.common.dto.response.ApiResponses;
import com.evo.product.application.dto.request.CreateOrUpdateProductRequest;
import com.evo.product.application.dto.response.ProductDTO;
import com.evo.product.application.service.impl.ProductCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductCommandService productCommandService;

    @PostMapping("/products")
    public ApiResponses<ProductDTO> createProduct(@RequestBody CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        ProductDTO productDTO = productCommandService.createProduct(createOrUpdateProductRequest);
        return ApiResponses.<ProductDTO>builder()
                .data(productDTO)
                .success(true)
                .code(201)
                .message("Product created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
