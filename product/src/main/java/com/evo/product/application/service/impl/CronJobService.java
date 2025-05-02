package com.evo.product.application.service.impl;

import com.evo.common.dto.event.ProductEvent;
import com.evo.common.dto.event.ProductSync;
import com.evo.common.enums.DiscountStatus;
import com.evo.product.application.mapper.SyncMapper;
import com.evo.product.domain.Discount;
import com.evo.product.domain.Product;
import com.evo.product.domain.repository.DiscountDomainRepository;
import com.evo.product.domain.repository.ProductDomainRepository;
import com.evo.product.infrastructure.adapter.rabbitmq.ProductEventRabbitMQService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CronJobService {
    private final ProductDomainRepository productDomainRepository;
    private final DiscountDomainRepository discountDomainRepository;
    private final SyncMapper syncMapper;
    private final ProductEventRabbitMQService productEventRabbitMQService;

    @Scheduled(cron = "0 * * * * *")
    public void syncProduct() {
        Instant now = Instant.now();
        List<DiscountStatus> discountStatuses = Arrays.asList(DiscountStatus.CANCELED, DiscountStatus.EXPIRED);
        List<Discount> discounts = discountDomainRepository.getAllNotIn(discountStatuses);
        for (Discount discount : discounts) {
            if (discount.getStartDate().isBefore(now) && discount.getEndDate().isAfter(now)) {
                discount.setStatus(DiscountStatus.ACTIVE);
            } else if (discount.getEndDate().isBefore(now)) {
                discount.setStatus(DiscountStatus.EXPIRED);
            }
        }
        discountDomainRepository.saveAll(discounts);
        List<Product> products = productDomainRepository.getAll();
        List<ProductSync> productSyncs = syncMapper.from(products);
        ProductEvent productEvent = new ProductEvent(productSyncs);
        productEventRabbitMQService.publishUpdateAllProductEvent(productEvent);
    }
}
