package com.evotek.elasticsearch.infrastructure.domainrepository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.evotek.elasticsearch.domain.ProductDocument;
import com.evotek.elasticsearch.domain.repository.ProductDomainRepository;
import com.evotek.elasticsearch.infrastructure.persistence.document.ProductDocumentEntity;
import com.evotek.elasticsearch.infrastructure.persistence.mapper.ProductDocumentMapper;
import com.evotek.elasticsearch.infrastructure.persistence.repository.ProductDocumentEntityRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ProductDomainRepositoryImpl
        extends AbstractDocumentDomainRepository<ProductDocument, ProductDocumentEntity, UUID>
        implements ProductDomainRepository {
    private final ProductDocumentMapper productDocumentMapper;
    private final ProductDocumentEntityRepository productDocumentEntityRepository;

    public ProductDomainRepositoryImpl(
            ProductDocumentMapper productDocumentMapper,
            ProductDocumentEntityRepository productDocumentEntityRepository) {
        super(productDocumentEntityRepository, productDocumentMapper);
        this.productDocumentEntityRepository = productDocumentEntityRepository;
        this.productDocumentMapper = productDocumentMapper;
    }

    @Override
    public List<ProductDocument> saveAll(List<ProductDocument> domains) {
        List<ProductDocumentEntity> productDocumentEntities = productDocumentMapper.toDocumentList(domains);
        return productDocumentMapper.toDomainModelList(
                productDocumentEntityRepository.saveAll(productDocumentEntities));
    }

    @Override
    public ProductDocument getById(UUID userId) {
        ProductDocumentEntity productDocumentEntity =
                productDocumentEntityRepository.findById(userId).orElse(null);
        return productDocumentMapper.toDomainModel(productDocumentEntity);
    }

    @Override
    public void deleteById(UUID userId) {
        productDocumentEntityRepository.deleteById(userId);
    }
}
