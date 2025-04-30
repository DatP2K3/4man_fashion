package com.evotek.elasticsearch.application.service.impl.command;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.evotek.elasticsearch.application.service.ProductCommandService;
import com.evotek.elasticsearch.domain.ProductDocument;
import com.evotek.elasticsearch.domain.command.SyncProductCmd;
import com.evotek.elasticsearch.domain.repository.ProductDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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
        product.update(syncProductCmd);
        productDomainRepository.save(product);
    }

    @Override
    public void delete(UUID productId) {
        productDomainRepository.deleteById(productId);
    }
}
