package com.evo.product.infrastructure.domainrepository;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.evo.common.enums.DiscountStatus;
import com.evo.common.repository.AbstractDomainRepository;
import com.evo.product.domain.*;
import com.evo.product.domain.repository.ProductDomainRepository;
import com.evo.product.infrastructure.persistence.entity.*;
import com.evo.product.infrastructure.persistence.mapper.*;
import com.evo.product.infrastructure.persistence.repository.*;
import com.evo.product.infrastructure.support.exception.AppErrorCode;
import com.evo.product.infrastructure.support.exception.AppException;

@Repository
public class ProductDomainRepositoryImpl extends AbstractDomainRepository<Product, ProductEntity, UUID>
        implements ProductDomainRepository {
    private final ProductEntityMapper productEntityMapper;
    private final ProductEntityRepository productEntityRepository;
    private final ProductVariantEntityRepository productVariantEntityRepository;
    private final ProductVariantEntityMapper productVariantEntityMapper;
    private final ProductImageEntityRepository productImageEntityRepository;
    private final ProductImageEntityMapper productImageEntityMapper;
    private final DiscountEntityRepository discountEntityRepository;
    private final DiscountEntityMapper discountEntityMapper;

    public ProductDomainRepositoryImpl(
            ProductEntityMapper productEntityMapper,
            ProductEntityRepository productEntityRepository,
            ProductVariantEntityRepository productVariantEntityRepository,
            ProductVariantEntityMapper productVariantEntityMapper,
            ProductImageEntityRepository productImageEntityRepository,
            ProductImageEntityMapper productImageEntityMapper,
            DiscountEntityRepository discountEntityRepository,
            DiscountEntityMapper discountEntityMapper) {
        super(productEntityRepository, productEntityMapper);
        this.productEntityRepository = productEntityRepository;
        this.productEntityMapper = productEntityMapper;
        this.productVariantEntityRepository = productVariantEntityRepository;
        this.productVariantEntityMapper = productVariantEntityMapper;
        this.productImageEntityRepository = productImageEntityRepository;
        this.productImageEntityMapper = productImageEntityMapper;
        this.discountEntityRepository = discountEntityRepository;
        this.discountEntityMapper = discountEntityMapper;
    }

    @Override
    public Product save(Product product) {
        List<ProductVariant> productVariants = product.getProductVariants();
        List<ProductVariantEntity> productVariantEntities = productVariantEntityMapper.toEntityList(productVariants);
        productVariantEntityRepository.saveAll(productVariantEntities);

        List<ProductImage> productImages = product.getProductImages();
        List<ProductImageEntity> productImageEntities = productImageEntityMapper.toEntityList(productImages);
        productImageEntityRepository.saveAll(productImageEntities);

        List<Discount> discounts = product.getDiscounts();
        List<DiscountEntity> discountEntities = discountEntityMapper.toEntityList(discounts);
        discountEntityRepository.saveAll(discountEntities);

        ProductEntity productEntity = productEntityMapper.toEntity(product);
        return this.enrich(productEntityMapper.toDomainModel(productEntityRepository.save(productEntity)));
    }

    @Override
    public List<Product> saveAll(List<Product> domains) {
        if (domains == null || domains.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProductVariantEntity> allProductVariantEntities = new ArrayList<>();
        List<ProductImageEntity> allProductImageEntities = new ArrayList<>();
        List<DiscountEntity> allDiscountEntities = new ArrayList<>();

        for (Product product : domains) {
            if (product.getProductVariants() != null && !product.getProductVariants().isEmpty()) {
                allProductVariantEntities.addAll(productVariantEntityMapper.toEntityList(product.getProductVariants()));
            }
            if (product.getProductImages() != null && !product.getProductImages().isEmpty()) {
                allProductImageEntities.addAll(productImageEntityMapper.toEntityList(product.getProductImages()));
            }
            if (product.getDiscounts() != null && !product.getDiscounts().isEmpty()) {
                allDiscountEntities.addAll(discountEntityMapper.toEntityList(product.getDiscounts()));
            }
        }

        if (!allProductVariantEntities.isEmpty()) {
            productVariantEntityRepository.saveAll(allProductVariantEntities);
        }
        if (!allProductImageEntities.isEmpty()) {
            productImageEntityRepository.saveAll(allProductImageEntities);
        }
        if (!allDiscountEntities.isEmpty()) {
            discountEntityRepository.saveAll(allDiscountEntities);
        }

        List<ProductEntity> productEntities = productEntityMapper.toEntityList(domains);
        List<ProductEntity> savedProductEntities = productEntityRepository.saveAll(productEntities);
        return this.enrichList(productEntityMapper.toDomainModelList(savedProductEntities));
    }

    @Override
    public Product getById(UUID uuid) {
        ProductEntity productEntity = productEntityRepository
                .findById(uuid)
                .orElseThrow(() -> new AppException(AppErrorCode.PRODUCT_NOT_FOUND));
        return this.enrich(productEntityMapper.toDomainModel(productEntity));
    }

    @Override
    protected List<Product> enrichList(List<Product> products) {
        if (products.isEmpty()) return products;

        List<UUID> productIds = products.stream().map(Product::getId).toList();

        Map<UUID, List<ProductVariant>> productVariantMap =
                productVariantEntityRepository.findByProductIdIn(productIds).stream()
                        .collect(Collectors.groupingBy(
                                ProductVariantEntity::getProductId,
                                Collectors.mapping(productVariantEntityMapper::toDomainModel, Collectors.toList())));

        Map<UUID, List<ProductImage>> productImageMap =
                productImageEntityRepository.findByProductIdIn(productIds).stream()
                        .collect(Collectors.groupingBy(
                                ProductImageEntity::getProductId,
                                Collectors.mapping(productImageEntityMapper::toDomainModel, Collectors.toList())));

        List<DiscountStatus> discountStatuses = Arrays.asList(DiscountStatus.CANCELED, DiscountStatus.EXPIRED);
        Map<UUID, List<Discount>> discountMap =
                discountEntityRepository.findByProductIdsAndStatusNotIn(productIds, discountStatuses).stream()
                        .collect(Collectors.groupingBy(
                                DiscountEntity::getProductId,
                                Collectors.mapping(discountEntityMapper::toDomainModel, Collectors.toList())));

        products.forEach(product -> {
            product.setProductVariants(
                    new ArrayList<>(productVariantMap.getOrDefault(product.getId(), Collections.emptyList())));
            product.setProductImages(
                    new ArrayList<>(productImageMap.getOrDefault(product.getId(), Collections.emptyList())));
            product.setDiscounts(new ArrayList<>(discountMap.getOrDefault(product.getId(), Collections.emptyList())));
            product.enrichDiscountInfo();
        });

        return products;
    }

    @Override
    public List<Product> getAllProductsWithNoDiscount() {
        List<ProductEntity> productEntities = productEntityRepository.findAll();
        List<Product> products = this.enrichList(productEntityMapper.toDomainModelList(productEntities));
        return products.stream()
                .filter(product ->
                        product.getDiscounts() == null || product.getDiscounts().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getAll() {
        List<ProductEntity> productEntities = productEntityRepository.getAll();
        return this.enrichList(productEntityMapper.toDomainModelList(productEntities));
    }
}
