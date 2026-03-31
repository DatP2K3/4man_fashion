package com.fourman.product.infrastructure.adapter.rabbitmq;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.common.dto.event.ProductVariantEvent;
import com.fourman.common.dto.event.ProductVariantSync;
import com.fourman.common.enums.OperationType;
import com.fourman.common.exception.ResponseException;
import com.fourman.product.infrastructure.persistence.entity.ProductVariantEntity;
import com.fourman.product.infrastructure.persistence.repository.ProductVariantEntityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventListener {
    private final ProductVariantEntityRepository productVariantEntityRepository;

    @RabbitListener(queues = "${rabbitmq.queue.product.update-variant}")
    @Transactional(rollbackFor = Exception.class)
    public void handleProductVariantUpdated(ProductVariantEvent event) {
        List<ProductVariantSync> syncs = event.getProductVariantSyncs();
        if (syncs == null || syncs.isEmpty()) {
            return;
        }

        // 1. Collect all variant IDs to update
        List<UUID> variantIds =
                syncs.stream().map(ProductVariantSync::getId).distinct().toList();

        // 2. Load ONLY the variant entities (1 query instead of 4)
        List<ProductVariantEntity> variants = productVariantEntityRepository.findAllById(variantIds);
        Map<UUID, ProductVariantEntity> variantMap =
                variants.stream().collect(Collectors.toMap(ProductVariantEntity::getId, Function.identity()));

        // 3. Apply quantity adjustments in memory
        for (ProductVariantSync sync : syncs) {
            ProductVariantEntity variant = variantMap.get(sync.getId());
            if (variant == null) {
                log.warn("ProductVariant not found: {}", sync.getId());
                continue;
            }
            if (sync.getOperationType() == OperationType.DECREASE) {
                variant.setQuantity(variant.getQuantity() - sync.getTotalQuantity());
            } else if (sync.getOperationType() == OperationType.INCREASE) {
                variant.setQuantity(variant.getQuantity() + sync.getTotalQuantity());
            } else {
                throw new ResponseException(
                        com.fourman.product.domain.exception.BadRequestError.INVALID_OPERATION_TYPE);
            }
        }

        // 4. Batch save only the variants (1 query instead of 4)
        productVariantEntityRepository.saveAll(variants);
        log.info("Synced {} product variant quantities", variants.size());
    }
}
