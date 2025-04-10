package com.evo.product.application.service.impl.command;

import com.evo.common.dto.event.SyncProductEvent;
import com.evo.common.dto.request.SyncProductRequest;
import com.evo.common.enums.KafkaTopic;
import com.evo.common.enums.SyncProductType;
import com.evo.product.application.dto.mapper.ProductDTOMapper;
import com.evo.product.application.dto.request.CreateOrUpdateProductRequest;
import com.evo.product.application.dto.response.ProductDTO;
import com.evo.product.application.mapper.CommandMapper;
import com.evo.product.application.mapper.SyncMapper;
import com.evo.product.application.service.impl.ProductCommandService;
import com.evo.product.domain.Product;
import com.evo.product.domain.command.CreateOrUpdateProductCmd;
import com.evo.product.domain.repository.ProductDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {
    private final ProductDomainRepository productDomainRepository;
    private final CommandMapper commandMapper;
    private final SyncMapper syncMapper;
    private final ProductDTOMapper productDTOMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public ProductDTO createProduct(CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        CreateOrUpdateProductCmd createOrUpdateProductCmd = commandMapper.from(createOrUpdateProductRequest);
        Product product = new Product(createOrUpdateProductCmd);
        product = productDomainRepository.save(product);

        SyncProductRequest syncProductRequest = syncMapper.from(product);
        SyncProductEvent syncProductEvent = SyncProductEvent.builder()
                .syncProductType(SyncProductType.PRODUCT_CREATED)
                .syncProductRequest(syncProductRequest)
                .build();
        kafkaTemplate.send(KafkaTopic.SYNC_PRODUCT_GROUP.getTopicName(), syncProductEvent);
        return productDTOMapper.domainModelToDTO(product);
    }
}
