package com.evo.product.application.service.impl.query;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.evo.product.application.dto.mapper.ProductDTOMapper;
import com.evo.common.dto.response.ProductDTO;
import com.evo.product.application.service.ProductQueryService;
import com.evo.product.domain.Product;
import com.evo.product.domain.repository.ProductDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductDomainRepository productDomainRepository;
    private final ProductDTOMapper productDTOMapper;

    @Override
    public ProductDTO getById(UUID id) {
        Product product = productDomainRepository.getById(id);
        return productDTOMapper.domainModelToDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProductsWithNoDiscount() {
        List<Product> products = productDomainRepository.getAllProductsWithNoDiscount();
       return productDTOMapper.domainModelsToDTOs(products);
    }
}
