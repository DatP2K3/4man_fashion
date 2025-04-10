package com.evotek.elasticsearch.application.service.impl.command;

import java.util.UUID;

import com.evotek.elasticsearch.domain.ProductDocument;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import com.evotek.elasticsearch.application.service.impl.ProductCommandService;
import com.evotek.elasticsearch.domain.command.SyncProductCmd;
import com.evotek.elasticsearch.domain.repository.UserDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {
    private final UserDomainRepository userDomainRepository;

    @Override
    public void create(SyncProductCmd syncProductCmd) {
        ProductDocument product = new ProductDocument(syncProductCmd);
       userDomainRepository.save(product);
    }

    @Override
    public void update(SyncProductCmd syncProductCmd) {
        ProductDocument product = userDomainRepository.getById(syncProductCmd.getId());
        product.update(syncProductCmd);
        userDomainRepository.save(product);
    }

    @Override
    public void delete(UUID selfUserID) {
        userDomainRepository.deleteById(selfUserID);
    }
}
