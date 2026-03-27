package com.fourman.elasticsearch.application.service.impl.command;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fourman.elasticsearch.application.service.ProductCommandService;
import com.fourman.elasticsearch.domain.ProductDocument;
import com.fourman.elasticsearch.domain.command.SyncProductCmd;
import com.fourman.elasticsearch.domain.repository.ProductDomainRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCommandServiceImpl implements ProductCommandService {
    private final ProductDomainRepository productDomainRepository;

    @Override
    public void create(SyncProductCmd syncProductCmd) {
        ProductDocument product = new ProductDocument(syncProductCmd);
        productDomainRepository.save(product);
    }

    @Override
    public void update(SyncProductCmd syncProductCmd) {
        ProductDocument product = productDomainRepository.getById(syncProductCmd.getId());
        if (product == null) {
            log.error("Product not found for id: {}", syncProductCmd.getId());
            return;
        }
        product.update(syncProductCmd);
        productDomainRepository.save(product);
    }

    @Override
    public void delete(UUID productId) {
        productDomainRepository.deleteById(productId);
    }
}
