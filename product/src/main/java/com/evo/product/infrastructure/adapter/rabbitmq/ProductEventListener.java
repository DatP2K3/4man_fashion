package com.evo.product.infrastructure.adapter.rabbitmq;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.ProductVariantEvent;
import com.evo.common.dto.event.ProductVariantSync;
import com.evo.product.application.mapper.SyncMapper;
import com.evo.product.domain.Product;
import com.evo.product.domain.command.UpdateProductVariantQuantityCmd;
import com.evo.product.domain.repository.ProductDomainRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventListener {
    private final SyncMapper syncMapper;
    private final ProductDomainRepository productDomainRepository;

    @RabbitListener(queues = "${rabbitmq.queue.product.update-variant}")
    public void handleProductVariantUpdated(ProductVariantEvent event) {
        List<ProductVariantSync> productVariantSyncs = event.getProductVariantSyncs();
        List<UpdateProductVariantQuantityCmd> cmds = syncMapper.fromProductVariantSyncs(productVariantSyncs);
        List<UUID> productIds = cmds.stream()
                .map(UpdateProductVariantQuantityCmd::getProductId)
                .distinct()
                .toList();
        List<Product> products = productDomainRepository.findAllByIds(productIds);
        Map<UUID, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, p -> p));
        Map<UUID, List<UpdateProductVariantQuantityCmd>> cmdMap =
                cmds.stream().collect(Collectors.groupingBy(UpdateProductVariantQuantityCmd::getProductId));
        for (UUID productId : productMap.keySet()) {
            Product product = productMap.get(productId);
            List<UpdateProductVariantQuantityCmd> productCmds = cmdMap.get(productId);
            if (productCmds != null) {
                for (UpdateProductVariantQuantityCmd cmd : productCmds) {
                    product.updateProductVariantQuantity(cmd);
                }
            }
        }
        productDomainRepository.saveAll(products);
    }
}
