package com.evo.product.application.service.impl.command;

import com.evo.common.dto.event.ProductSync;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.ProductEvent;
import com.evo.product.application.dto.mapper.ProductDTOMapper;
import com.evo.product.application.dto.request.CreateOrUpdateDiscountRequest;
import com.evo.product.application.dto.request.CreateOrUpdateProductRequest;
import com.evo.product.application.dto.response.ProductDTO;
import com.evo.product.application.mapper.CommandMapper;
import com.evo.product.application.mapper.SyncMapper;
import com.evo.product.application.service.ProductCommandService;
import com.evo.product.domain.Product;
import com.evo.product.domain.command.CreateOrUpdateDiscountCmd;
import com.evo.product.domain.command.CreateOrUpdateProductCmd;
import com.evo.product.domain.repository.ProductDomainRepository;
import com.evo.product.infrastructure.adapter.rabbitmq.ProductEventRabbitMQService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
class ProductCommandServiceImpl implements ProductCommandService {
    private final ProductDomainRepository productDomainRepository;
    private final CommandMapper commandMapper;
    private final SyncMapper syncMapper;
    private final ProductDTOMapper productDTOMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ProductEventRabbitMQService productEventRabbitMQService;

    @Override
    public ProductDTO createProduct(CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        CreateOrUpdateProductCmd createOrUpdateProductCmd = commandMapper.from(createOrUpdateProductRequest);
        Product product = new Product(createOrUpdateProductCmd);
        product = productDomainRepository.save(product);

        ProductSync productSync = syncMapper.from(product);
        ProductEvent productEvent = new ProductEvent(List.of(productSync));
        productEventRabbitMQService.publishProductCreatedEvent(productEvent);
        return productDTOMapper.domainModelToDTO(product);
    }

    @Override
    public ProductDTO updateProduct(CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        CreateOrUpdateProductCmd createOrUpdateProductCmd = commandMapper.from(createOrUpdateProductRequest);
        Product product = productDomainRepository.getById(createOrUpdateProductCmd.getId());
        product.update(createOrUpdateProductCmd);
        product = productDomainRepository.save(product);

        ProductSync productSync = syncMapper.from(product);
        ProductEvent productEvent = new ProductEvent(List.of(productSync));
        productEventRabbitMQService.publishProductUpdatedEvent(productEvent);
        return productDTOMapper.domainModelToDTO(product);
    }

    @Override
    public ProductDTO createDiscount(CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest) {
        CreateOrUpdateDiscountCmd createOrUpdateDiscountCmd = commandMapper.from(createOrUpdateDiscountRequest);
        Product product = productDomainRepository.getById(createOrUpdateDiscountCmd.getProductId());
        product.createDiscount(createOrUpdateDiscountCmd);
        product = productDomainRepository.save(product);

        ProductSync productSync = syncMapper.from(product);
        ProductEvent productEvent = new ProductEvent(List.of(productSync));
        productEventRabbitMQService.publishProductUpdatedEvent(productEvent);
        return productDTOMapper.domainModelToDTO(product);
    }

    @Override
    public ProductDTO updateDiscount(CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest) {
        CreateOrUpdateDiscountCmd createOrUpdateDiscountCmd = commandMapper.from(createOrUpdateDiscountRequest);
        Product product = productDomainRepository.getById(createOrUpdateDiscountCmd.getProductId());
        product.updateDiscount(createOrUpdateDiscountCmd);
        product = productDomainRepository.save(product);

        ProductSync productSync = syncMapper.from(product);
        ProductEvent productEvent = new ProductEvent(List.of(productSync));
        return productDTOMapper.domainModelToDTO(product);
    }
}
