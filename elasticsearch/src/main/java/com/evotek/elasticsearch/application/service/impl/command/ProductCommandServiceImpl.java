package com.evotek.elasticsearch.application.service.impl.command;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.evotek.elasticsearch.application.service.ProductCommandService;
import com.evotek.elasticsearch.domain.ProductDocument;
import com.evotek.elasticsearch.domain.command.SyncProductCmd;
import com.evotek.elasticsearch.domain.repository.ProductDomainRepository;

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
