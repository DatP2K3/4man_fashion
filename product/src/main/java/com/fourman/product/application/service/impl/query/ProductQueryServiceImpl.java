package com.fourman.product.application.service.impl.query;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fourman.common.dto.response.ProductDTO;
import com.fourman.product.application.dto.mapper.ProductDTOMapper;
import com.fourman.product.application.service.ProductQueryService;
import com.fourman.product.domain.Product;
import com.fourman.product.domain.repository.ProductDomainRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
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
