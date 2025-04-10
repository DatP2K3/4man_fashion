package com.evo.product.infrastructure.domainrepository;

import com.evo.common.repository.AbstractDomainRepository;
import com.evo.product.domain.*;
import com.evo.product.domain.repository.CategoryDomainRepository;
import com.evo.product.domain.repository.ProductDomainRepository;
import com.evo.product.infrastructure.persistence.entity.*;
import com.evo.product.infrastructure.persistence.mapper.*;
import com.evo.product.infrastructure.persistence.repository.*;
import com.evo.product.infrastructure.support.exception.AppErrorCode;
import com.evo.product.infrastructure.support.exception.AppException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProductDomainRepositoryImpl extends AbstractDomainRepository<Product, ProductEntity, UUID>
        implements ProductDomainRepository {
    private final ProductEntityMapper productEntityMapper;
    private final ProductEntityRepository productEntityRepository;
    private final ProductVariantEntityRepository productVariantEntityRepository;
    private final ProductVariantEntityMapper productVariantEntityMapper;
    private final ProductImageEntityRepository productImageEntityRepository;
    private final ProductImageEntityMapper productImageEntityMapper;
    public ProductDomainRepositoryImpl(
            ProductEntityMapper productEntityMapper,
            ProductEntityRepository productEntityRepository,
            ProductVariantEntityRepository productVariantEntityRepository,
            ProductVariantEntityMapper productVariantEntityMapper,
            ProductImageEntityRepository productImageEntityRepository,
            ProductImageEntityMapper productImageEntityMapper) {
        super(productEntityRepository, productEntityMapper);
       this.productEntityRepository = productEntityRepository;
        this.productEntityMapper = productEntityMapper;
        this.productVariantEntityRepository = productVariantEntityRepository;
        this.productVariantEntityMapper = productVariantEntityMapper;
        this.productImageEntityRepository = productImageEntityRepository;
        this.productImageEntityMapper = productImageEntityMapper;
    }

    @Override
    public Product save(Product product) {
        List<ProductVariant> productVariants = product.getProductVariants();
        List<ProductVariantEntity> productVariantEntities = productVariantEntityMapper.toEntityList(productVariants);
        productVariantEntityRepository.saveAll(productVariantEntities);

        List<ProductImage> productImages = product.getProductImages();
        List<ProductImageEntity> productImageEntities = productImageEntityMapper.toEntityList(productImages);
        productImageEntityRepository.saveAll(productImageEntities);

        ProductEntity productEntity = productEntityMapper.toEntity(product);
        return this.enrich(productEntityMapper.toDomainModel(productEntityRepository.save(productEntity)));
    }

    @Override
    public Product getById(UUID uuid) {
        ProductEntity productEntity = productEntityRepository.findById(uuid).orElseThrow(() -> new AppException(AppErrorCode.PRODUCT_NOT_FOUND));
        return this.enrich(productEntityMapper.toDomainModel(productEntity));
    }

    @Override
    protected List<Product> enrichList(List<Product> products) {
        if(products.isEmpty()) return products;

        List<UUID> productIds = products.stream().map(Product::getId).toList();

        Map<UUID, List<ProductVariant>> productVariantMap =
                productVariantEntityRepository.findByProductIdIn(productIds).stream()
                        .collect(Collectors.groupingBy(
                                ProductVariantEntity::getProductId,
                                Collectors.mapping(productVariantEntityMapper::toDomainModel, Collectors.toList())
                        ));

        Map<UUID, List<ProductImage>> productImageMap =
                productImageEntityRepository.findByProductIdIn(productIds).stream()
                        .collect(Collectors.groupingBy(
                                ProductImageEntity::getProductId,
                                Collectors.mapping(productImageEntityMapper::toDomainModel, Collectors.toList())
                        ));

        products.forEach(product -> {
            product.setProductVariants(productVariantMap.getOrDefault(product.getId(), Collections.emptyList()));
            product.setProductImages(productImageMap.getOrDefault(product.getId(), Collections.emptyList()));
        });

        return products;
    }
}
