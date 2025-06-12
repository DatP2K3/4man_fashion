package com.evo.product.application.service.impl.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.evo.common.dto.event.FileEvent;
import com.evo.common.dto.event.ProductEvent;
import com.evo.common.dto.event.ProductSync;
import com.evo.common.dto.response.ProductDTO;
import com.evo.common.enums.FileUsageStatus;
import com.evo.product.application.dto.mapper.ProductDTOMapper;
import com.evo.product.application.dto.request.CreateOrUpdateDiscountRequest;
import com.evo.product.application.dto.request.CreateOrUpdateProductRequest;
import com.evo.product.application.mapper.CommandMapper;
import com.evo.product.application.mapper.SyncMapper;
import com.evo.product.application.service.ProductCommandService;
import com.evo.product.domain.Product;
import com.evo.product.domain.ProductImage;
import com.evo.product.domain.command.CreateOrUpdateDiscountCmd;
import com.evo.product.domain.command.CreateOrUpdateProductCmd;
import com.evo.product.domain.command.CreateOrUpdateProductImageCmd;
import com.evo.product.domain.repository.ProductDomainRepository;
import com.evo.product.infrastructure.adapter.rabbitmq.FileEventRabbitMQService;
import com.evo.product.infrastructure.adapter.rabbitmq.ProductEventRabbitMQService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ProductCommandServiceImpl implements ProductCommandService {
    private final ProductDomainRepository productDomainRepository;
    private final CommandMapper commandMapper;
    private final SyncMapper syncMapper;
    private final ProductDTOMapper productDTOMapper;
    private final ProductEventRabbitMQService productEventRabbitMQService;
    private final FileEventRabbitMQService fileEventRabbitMQService;

    @Override
    public ProductDTO createProduct(CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        CreateOrUpdateProductCmd createOrUpdateProductCmd = commandMapper.from(createOrUpdateProductRequest);
        Product product = new Product(createOrUpdateProductCmd);
        product = productDomainRepository.save(product);

        ProductSync productSync = syncMapper.from(product);
        ProductEvent productEvent = new ProductEvent(List.of(productSync));
        productEventRabbitMQService.publishProductCreatedEvent(productEvent);

        List<CreateOrUpdateProductImageCmd> productImages = createOrUpdateProductCmd.getProductImages();
        Map<UUID, FileUsageStatus> usageMap = productImages.stream()
                .collect(Collectors.toMap(CreateOrUpdateProductImageCmd::getFileId, img -> FileUsageStatus.USED));
        FileEvent fileEvent = new FileEvent(usageMap);
        fileEventRabbitMQService.publishFileUpdatedEvent(fileEvent);

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

        Map<UUID, FileUsageStatus> fileStatusMap = new HashMap<>();

        List<UUID> newImageFileIds = product.getProductImages().stream()
                .filter(img -> !img.getDeleted())
                .map(ProductImage::getFileId)
                .collect(Collectors.toList());

        List<UUID> oldImageFileIds = product.getProductImages() != null
                ? product.getProductImages().stream()
                        .filter(img -> !img.getDeleted())
                        .map(ProductImage::getFileId)
                        .collect(Collectors.toList())
                : List.of();

        // Mark old images that are no longer used as UNUSED
        oldImageFileIds.stream()
                .filter(fileId -> !newImageFileIds.contains(fileId))
                .forEach(fileId -> fileStatusMap.put(fileId, FileUsageStatus.UNUSED));

        // Mark new images as USED
        newImageFileIds.forEach(fileId -> fileStatusMap.put(fileId, FileUsageStatus.USED));

        // Only send event if there are file status changes
        if (!fileStatusMap.isEmpty()) {
            FileEvent fileEvent = new FileEvent(fileStatusMap);
            fileEventRabbitMQService.publishFileUpdatedEvent(fileEvent);
        }
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
        productEventRabbitMQService.publishProductUpdatedEvent(productEvent);
        return productDTOMapper.domainModelToDTO(product);
    }

    @Override
    public ProductDTO toggleProductVisibility(UUID id) {
        Product product = productDomainRepository.getById(id);
        product.toggleVisibility();
        product = productDomainRepository.save(product);

        ProductSync productSync = syncMapper.from(product);
        ProductEvent productEvent = new ProductEvent(List.of(productSync));
        productEventRabbitMQService.publishProductUpdatedEvent(productEvent);

        List<ProductImage> productImages = product.getProductImages();
        Map<UUID, FileUsageStatus> usageMap = new HashMap<>();
        if (Boolean.TRUE.equals(product.getHidden())) {
            usageMap = productImages.stream()
                    .collect(Collectors.toMap(ProductImage::getFileId, img -> FileUsageStatus.UNUSED));
        } else {
            usageMap = productImages.stream()
                    .collect(Collectors.toMap(ProductImage::getFileId, img -> FileUsageStatus.USED));
        }
        FileEvent fileEvent = new FileEvent(usageMap);
        fileEventRabbitMQService.publishFileUpdatedEvent(fileEvent);
        return productDTOMapper.domainModelToDTO(product);
    }
}
