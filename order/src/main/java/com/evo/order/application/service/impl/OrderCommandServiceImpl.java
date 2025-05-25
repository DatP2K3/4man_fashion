package com.evo.order.application.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.evo.common.dto.event.UseCashbackEvent;
import com.evo.order.infrastructure.adapter.rabbitmq.CashbackEventRabbitMQService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evo.common.dto.event.PushNotificationEvent;
import com.evo.common.dto.request.GetPaymentUrlRequest;
import com.evo.common.dto.response.CartDTO;
import com.evo.common.dto.response.OrderDTO;
import com.evo.common.dto.response.ProfileDTO;
import com.evo.common.dto.response.ShippingAddressDTO;
import com.evo.common.dto.response.ShopAddressDTO;
import com.evo.common.enums.*;
import com.evo.order.application.dto.mapper.OrderDTOMapper;
import com.evo.order.application.dto.request.*;
import com.evo.order.application.dto.response.GHNOrderDTO;
import com.evo.order.application.dto.response.OrderFeeDTO;
import com.evo.order.application.mapper.CommandMapper;
import com.evo.order.application.service.OrderCommandService;
import com.evo.order.application.service.OrderQueryService;
import com.evo.order.domain.Order;
import com.evo.order.domain.OrderItem;
import com.evo.order.domain.command.CreateOrderCmd;
import com.evo.order.domain.command.UpdateOrderStatusCmd;
import com.evo.order.domain.repository.OrderDomainRepository;
import com.evo.order.infrastructure.adapter.cart.client.CartClient;
import com.evo.order.infrastructure.adapter.ghn.client.GHNClient;
import com.evo.order.infrastructure.adapter.payment.client.PaymentClient;
import com.evo.order.infrastructure.adapter.profile.client.ProfileClient;
import com.evo.order.infrastructure.adapter.rabbitmq.NotiEventRabbitMQService;
import com.evo.order.infrastructure.adapter.shopinfo.client.ShopInfoClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderCommandServiceImpl implements OrderCommandService {
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

    @Override
    @Transactional
    public OrderDTO create(CreateOrderRequest request) {
        CreateOrderCmd createOrderCmd = commandMapper.from(request);
        ProfileDTO profileDTO = profileClient.getProfile().getData();
        OrderFeeDTO orderFeeDTO = orderQueryService.caculateFeeByAddressId(request.getToAddressId());
        ShippingAddressDTO toAddress = profileDTO.getListShippingAddress().stream()
                .filter(item -> item.getId().equals(request.getToAddressId()))
                .findFirst()
                .orElse(null);

        ShopAddressDTO fromAddress = null;
        ShopAddressDTO returnAddress = null;
        List<ShopAddressDTO> shopAddressDTOS = shopInfoClient.getShopAddress().getData();

        for (ShopAddressDTO shopAddressDTO : shopAddressDTOS) {
            if (shopAddressDTO.getType().equals(ShopAddressType.SEND_ADDRESS)) {
                fromAddress = shopAddressDTO;
            } else if (shopAddressDTO.getType().equals(ShopAddressType.RETURN_ADDRESS)) {
                returnAddress = shopAddressDTO;
            }
        }

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
            order.setPaymentUrl(paymentUrl);
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

        return orderDTOMapper.domainModelToDTO(order);
    }

    @Override
    @Transactional
    public void delete(CancelOrderRequest cancelOrderRequest) {
        if (cancelOrderRequest.getOrderIds() == null
                || cancelOrderRequest.getOrderIds().isEmpty()) {
            return;
        }
        List<UUID> orderIds = cancelOrderRequest.getOrderIds();
        List<Order> orders = orderDomainRepository.getByIds(orderIds);
        List<String> ghnOrderCodes = orders.stream()
                .filter(order -> order.getGHNOrderCode() != null)
                .map(Order::getGHNOrderCode)
                .toList();
        for (Order order : orders) {
            if (order.getOrderStatus().equals(OrderStatus.PENDING_SHIPMENT)
                    || order.getOrderStatus().equals(OrderStatus.WAITING_FOR_PICKUP)) {
                order.setOrderStatus(OrderStatus.CANCELLED);
                List<OrderItem> orderItems = order.getOrderItems();
                for (OrderItem orderItem : orderItems) {
                    orderItem.setDeleted(true);
                }

                PushNotificationEvent pushNotificationEvent = PushNotificationEvent.builder()
                        .title("Đơn hàng" + order.getOrderCode())
                        .body("Đơn hàng đã bị hủy")
                        .userId(order.getUserId())
                        .data(Map.of("orderCode", order.getOrderCode()))
                        .build();
                notiEventRabbitMQService.publishNotiPushEvent(pushNotificationEvent);
            }
        }
        orderDomainRepository.saveAll(orders);
        if (!ghnOrderCodes.isEmpty()) {
            PrintOrCancelGHNOrderRequest request = new PrintOrCancelGHNOrderRequest(ghnOrderCodes);
            ghnClient.cancelShippingOrder(request);
        }
    }

    @Override
    public List<OrderDTO> createGHNOrder(CreatShippingOrderRequest request) {
        if (request.getOrderIds().isEmpty() || request.getOrderIds() == null) {
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
                    .serviceTypeId(2)
                    .paymentTypeId(2)
                    .note(order.getNote())
                    .requiredNote("CHOXEMHANGKHONGTHU")
                    .build();

            if (order.getPaymentMethod() == PaymentMethod.ONLINE) {
                createGHNOrderRequest.setPaymentTypeId(1);
                createGHNOrderRequest.setCodAmount(0L);
            }

            GHNOrderDTO ghnOrderDTO =
                    ghnClient.createShippingOrder(createGHNOrderRequest).getData();
            order.setGHNOrderCode(ghnOrderDTO.getOrderCode());
            order.setOrderStatus(OrderStatus.WAITING_FOR_PICKUP);

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
            order.setPaymentStatus(PaymentStatus.PAID);
            order.setOrderStatus(OrderStatus.PENDING_SHIPMENT);
        } else {
            order.setPaymentStatus(PaymentStatus.FAILED);
        }
        orderDomainRepository.save(order);
    }
}
