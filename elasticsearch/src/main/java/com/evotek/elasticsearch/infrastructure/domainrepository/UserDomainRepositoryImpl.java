package com.evotek.elasticsearch.infrastructure.domainrepository;

import java.util.List;
import java.util.UUID;

import com.evotek.elasticsearch.domain.ProductDocument;
import com.evotek.elasticsearch.infrastructure.persistence.document.ProductDocumentEntity;
import org.springframework.stereotype.Repository;

import com.evotek.elasticsearch.domain.repository.UserDomainRepository;
import com.evotek.elasticsearch.infrastructure.persistence.mapper.ProductDocumentMapper;
import com.evotek.elasticsearch.infrastructure.persistence.repository.ProductDocumentRepository;
import com.evotek.elasticsearch.infrastructure.support.exception.AppErrorCode;
import com.evotek.elasticsearch.infrastructure.support.exception.AppException;

@Repository
public class UserDomainRepositoryImpl extends AbstractDocumentDomainRepository<ProductDocument, ProductDocumentEntity, UUID>
        implements UserDomainRepository {
    private final ProductDocumentMapper productDocumentMapper;
    private final ProductDocumentRepository productDocumentRepository;

    public UserDomainRepositoryImpl(
            ProductDocumentMapper productDocumentMapper, ProductDocumentRepository productDocumentRepository) {
        super(productDocumentRepository, productDocumentMapper);
        this.productDocumentRepository = productDocumentRepository;
        this.productDocumentMapper = productDocumentMapper;
    }

    @Override
    public List<ProductDocument> saveAll(List<ProductDocument> domains) {
        List<ProductDocumentEntity> productDocumentEntities = productDocumentMapper.toDocumentList(domains);
        return productDocumentMapper.toDomainModelList(productDocumentRepository.saveAll(productDocumentEntities));
    }

    @Override
    public ProductDocument getById(UUID userId) {
        ProductDocumentEntity productDocumentEntity = productDocumentRepository
                .findById(userId)
                .orElseThrow(() -> new AppException(AppErrorCode.USER_NOT_FOUND));
        return productDocumentMapper.toDomainModel(productDocumentEntity);
    }

    @Override
    public void deleteById(UUID userId) {
        productDocumentRepository.deleteById(userId);
    }
}
