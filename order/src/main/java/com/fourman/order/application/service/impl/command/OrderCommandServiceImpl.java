package com.fourman.order.application.service.impl.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.common.dto.event.ProductVariantEvent;
import com.fourman.common.dto.event.ProductVariantSync;
import com.fourman.common.dto.event.PushNotificationEvent;
import com.fourman.common.dto.event.UseCashbackEvent;
import com.fourman.common.dto.request.GetPaymentUrlRequest;
import com.fourman.common.dto.response.CartDTO;
import com.fourman.common.dto.response.OrderDTO;
import com.fourman.common.dto.response.ProfileDTO;
import com.fourman.common.dto.response.ShippingAddressDTO;
import com.fourman.common.dto.response.ShopAddressDTO;
import com.fourman.common.enums.*;
import com.fourman.order.application.dto.mapper.OrderDTOMapper;
import com.fourman.order.application.dto.request.*;
import com.fourman.order.application.dto.response.GHNOrderDTO;
import com.fourman.order.application.dto.response.OrderFeeDTO;
import com.fourman.order.application.mapper.CommandMapper;
import com.fourman.order.application.service.OrderCommandService;
import com.fourman.order.application.service.OrderQueryService;
import com.fourman.order.domain.Order;
import com.fourman.order.domain.command.CreateOrderCmd;
import com.fourman.order.domain.command.UpdateOrderStatusCmd;
import com.fourman.order.domain.repository.OrderDomainRepository;
import com.fourman.order.infrastructure.adapter.cart.client.CartClient;
import com.fourman.order.infrastructure.adapter.ghn.client.GHNClient;
import com.fourman.order.infrastructure.adapter.payment.client.PaymentClient;
import com.fourman.order.infrastructure.adapter.profile.client.ProfileClient;
import com.fourman.order.infrastructure.adapter.rabbitmq.CashbackEventRabbitMQService;
import com.fourman.order.infrastructure.adapter.rabbitmq.NotiEventRabbitMQService;
import com.fourman.order.infrastructure.adapter.rabbitmq.ProductEventRabbitMQService;
import com.fourman.order.infrastructure.adapter.shopinfo.client.ShopInfoClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderCommandServiceImpl implements OrderCommandService {
    private static final int GHN_SERVICE_TYPE_STANDARD = 2;
    private static final int GHN_PAYMENT_TYPE_COD = 2;
    private static final int GHN_PAYMENT_TYPE_PREPAID = 1;

    private final CommandMapper commandMapper;
    private final CartClient cartClient;
    private final ShopInfoClient shopInfoClient;
    private final PaymentClient paymentClient;
    private final ProfileClient profileClient;
    private final GHNClient ghnClient;
    private final OrderQueryService orderQueryService;
    private final OrderDomainRepository orderDomainRepository;
    private final OrderDTOMapper orderDTOMapper;
    private final NotiEventRabbitMQService notiEventRabbitMQService;
    private final CashbackEventRabbitMQService cashbackEventRabbitMQService;
    private final ProductEventRabbitMQService productEventRabbitMQService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO create(CreateOrderRequest request) {
        CreateOrderCmd createOrderCmd = commandMapper.from(request);
        ProfileDTO profileDTO = profileClient.getProfile().getData();
        OrderFeeDTO orderFeeDTO = orderQueryService.calculateFeeByAddressId(request.getToAddressId());
        ShippingAddressDTO toAddress = profileDTO.getListShippingAddress().stream()
                .filter(item -> item.getId().equals(request.getToAddressId()))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Shipping address not found: " + request.getToAddressId()));

        List<ShopAddressDTO> shopAddressDTOS = shopInfoClient.getShopAddress().getData();

        ShopAddressDTO fromAddress = shopAddressDTOS.stream()
                .filter(addr -> addr.getType().equals(ShopAddressType.SEND_ADDRESS))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Send address not configured"));

        ShopAddressDTO returnAddress = shopAddressDTOS.stream()
                .filter(addr -> addr.getType().equals(ShopAddressType.RETURN_ADDRESS))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Return address not configured"));

        CartDTO cartDTO = cartClient.getCart().getData();

        createOrderCmd.enrichInfo(profileDTO, orderFeeDTO, fromAddress, toAddress, returnAddress, cartDTO);
        Order order = new Order(createOrderCmd);
        GetPaymentUrlRequest getPaymentUrlRequest = GetPaymentUrlRequest.builder()
                .orderId(order.getId())
                .orderCode(order.getOrderCode())
                .totalPrice(order.getTotalPrice() + order.getShipmentFee())
                .build();

        if (order.getPaymentMethod().equals(PaymentMethod.ONLINE)) {
            String paymentUrl =
                    paymentClient.getPaymentUrl(getPaymentUrlRequest).getData();
            order.assignPaymentUrl(paymentUrl);
        }

        order = orderDomainRepository.save(order);
        cartClient.emptyCart(cartDTO.getId());

        PushNotificationEvent pushNotificationEvent = PushNotificationEvent.builder()
                .title("Đơn hàng" + order.getOrderCode())
                .body("Đơn hàng đã được tạo thành công")
                .userId(order.getUserId())
                .data(Map.of("orderCode", order.getOrderCode()))
                .build();
        notiEventRabbitMQService.publishNotiPushEvent(pushNotificationEvent);

        UseCashbackEvent useCashbackEvent = UseCashbackEvent.builder()
                .userId(order.getUserId())
                .orderId(order.getId())
                .amount(order.getCashbackUsed())
                .type(CashbackTransactionType.USED)
                .build();
        cashbackEventRabbitMQService.publishUseCashbackEvent(useCashbackEvent);

        List<ProductVariantSync> productVariantSyncs = order.getOrderItems().stream()
                .map(orderItem -> ProductVariantSync.builder()
                        .id(orderItem.getProductVariantId())
                        .productId(orderItem.getProductId())
                        .totalQuantity(orderItem.getQuantity())
                        .operationType(OperationType.DECREASE)
                        .build())
                .toList();
        ProductVariantEvent productVariantEvent = new ProductVariantEvent(productVariantSyncs);
        productEventRabbitMQService.publishProductPushEvent(productVariantEvent);

        return orderDTOMapper.domainModelToDTO(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(CancelOrderRequest cancelOrderRequest) {
        if (cancelOrderRequest.getOrderIds() == null
                || cancelOrderRequest.getOrderIds().isEmpty()) {
            return;
        }
        List<UUID> orderIds = cancelOrderRequest.getOrderIds();
        List<Order> orders = orderDomainRepository.getByIds(orderIds);
        List<String> ghnOrderCodes = orders.stream()
                .filter(order -> order.getGhnOrderCode() != null)
                .map(Order::getGhnOrderCode)
                .toList();

        List<ProductVariantSync> productVariantSyncs = new ArrayList<>();

        for (Order order : orders) {
            if (order.isCancellable()) {
                order.cancel();

                PushNotificationEvent pushNotificationEvent = PushNotificationEvent.builder()
                        .title("Đơn hàng " + order.getOrderCode())
                        .body("Đơn hàng đã bị hủy")
                        .userId(order.getUserId())
                        .data(Map.of("orderCode", order.getOrderCode()))
                        .build();
                notiEventRabbitMQService.publishNotiPushEvent(pushNotificationEvent);

                order.getOrderItems().stream()
                        .map(orderItem -> ProductVariantSync.builder()
                                .id(orderItem.getProductVariantId())
                                .productId(orderItem.getProductId())
                                .totalQuantity(orderItem.getQuantity())
                                .operationType(OperationType.INCREASE)
                                .build())
                        .forEach(productVariantSyncs::add);
            }
        }
        orderDomainRepository.saveAll(orders);
        ProductVariantEvent productVariantEvent = new ProductVariantEvent(productVariantSyncs);
        productEventRabbitMQService.publishProductPushEvent(productVariantEvent);
        if (!ghnOrderCodes.isEmpty()) {
            PrintOrCancelGHNOrderRequest request = new PrintOrCancelGHNOrderRequest(ghnOrderCodes);
            ghnClient.cancelShippingOrder(request);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<OrderDTO> createGHNOrder(CreatShippingOrderRequest request) {
        if (request.getOrderIds() == null || request.getOrderIds().isEmpty()) {
            return List.of();
        }
        List<Order> orders = orderDomainRepository.getByIds(request.getOrderIds());
        for (Order order : orders) {
            CreateGHNOrderRequest createGHNOrderRequest = CreateGHNOrderRequest.builder()
                    .fromName(order.getFromName())
                    .fromPhone(order.getFromPhoneNumber())
                    .fromAddress(order.getFromAddressLine1() + order.getFromAddressLine2())
                    .fromDistrictName(order.getFromDistrict())
                    .fromWardName(order.getFromWard())
                    .fromProvinceName(order.getFromCity())
                    .toName(order.getToName())
                    .toPhone(order.getToPhoneNumber())
                    .toAddress(order.getToAddressLine1() + order.getToAddressLine2())
                    .toDistrictName(order.getToDistrict())
                    .toWardName(order.getToWard())
                    .toProvinceName(order.getToCity())
                    .returnPhone(order.getReturnPhoneNumber())
                    .returnAddress(order.getReturnAddressLine1() + order.getReturnAddressLine2())
                    .returnDistrictName(order.getReturnDistrict())
                    .returnWardName(order.getReturnWard())
                    .returnProvinceName(order.getReturnCity())
                    .clientOrderCode(order.getOrderCode())
                    .codAmount(order.getTotalPrice() + order.getShipmentFee() - order.getCashbackUsed())
                    .content("4Man Fashion Luxury")
                    .weight(order.getTotalWeight())
                    .length(order.getTotalLength())
                    .width(order.getTotalWidth())
                    .height(order.getTotalHeight())
                    .serviceTypeId(GHN_SERVICE_TYPE_STANDARD)
                    .paymentTypeId(GHN_PAYMENT_TYPE_COD)
                    .note(order.getNote())
                    .requiredNote("CHOXEMHANGKHONGTHU")
                    .build();

            if (order.getPaymentMethod() == PaymentMethod.ONLINE) {
                createGHNOrderRequest.setPaymentTypeId(GHN_PAYMENT_TYPE_PREPAID);
                createGHNOrderRequest.setCodAmount(0L);
            }

            GHNOrderDTO ghnOrderDTO =
                    ghnClient.createShippingOrder(createGHNOrderRequest).getData();
            order.assignShipping(ghnOrderDTO.getOrderCode());

            PushNotificationEvent pushNotificationEvent = PushNotificationEvent.builder()
                    .title("Đơn hàng" + order.getOrderCode())
                    .body("Đơn hàng đã được sắp xếp vận chuyển")
                    .userId(order.getUserId())
                    .data(Map.of("orderCode", order.getOrderCode()))
                    .build();
            notiEventRabbitMQService.publishNotiPushEvent(pushNotificationEvent);
        }
        orders = orderDomainRepository.saveAll(orders);
        return orderDTOMapper.domainModelsToDTOs(orders);
    }

    @Override
    public void updateStatus(UpdateOrderStatusCmd updateOrderStatusCmd) {
        Order order = orderDomainRepository.getByOrderCode(updateOrderStatusCmd.getOrderCode());
        if (updateOrderStatusCmd.getStatus() == TransactionStatus.SUCCESS) {
            order.markPaymentSuccess();
        } else {
            order.markPaymentFailed();
        }
        orderDomainRepository.save(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void printGHNOrder(List<String> ghnOrderCodes) {
        List<Order> orders = orderDomainRepository.getByGhnOrderCodeIn(ghnOrderCodes);
        for (Order order : orders) {
            order.markAsPrinted();
        }
        orderDomainRepository.saveAll(orders);
    }
}
