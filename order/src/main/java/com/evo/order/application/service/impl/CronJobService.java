package com.evo.order.application.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.evo.order.infrastructure.adapter.rabbitmq.CashbackEventRabbitMQService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.ProcessCashbackEvent;
import com.evo.common.dto.event.PushNotificationEvent;
import com.evo.common.enums.CashbackTransactionType;
import com.evo.common.enums.OrderStatus;
import com.evo.order.application.dto.request.GetGHNOrderDetailRequest;
import com.evo.order.application.dto.response.GHNOrderDetailDTO;
import com.evo.order.application.dto.response.GHNOrderLogDTO;
import com.evo.order.domain.Order;
import com.evo.order.domain.repository.OrderDomainRepository;
import com.evo.order.infrastructure.adapter.ghn.client.GHNClient;
import com.evo.order.infrastructure.adapter.rabbitmq.NotiEventRabbitMQService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CronJobService {
    private final OrderDomainRepository orderDomainRepository;
    private final GHNClient ghnClient;
    private final NotiEventRabbitMQService notiEventRabbitMQService;
    private final CashbackEventRabbitMQService cashbackEventRabbitMQService;

    @Scheduled(cron = "0 */3 * * * *")
    public void syncOrderStatus() {
        List<OrderStatus> orderStatuses =
                Arrays.asList(OrderStatus.PENDING_SHIPMENT, OrderStatus.WAITING_FOR_PICKUP, OrderStatus.IN_TRANSIT);
        List<Order> orders = orderDomainRepository.getAllOrderWithStatusIn(orderStatuses);
        orders.stream().filter(order -> order.getGHNOrderCode() != null).forEach(order -> {
            GetGHNOrderDetailRequest getGHNFeeRequest = new GetGHNOrderDetailRequest(order.getGHNOrderCode());
            GHNOrderDetailDTO ghnOrderDetailDTO =
                    ghnClient.getOrderDetail(getGHNFeeRequest).getData();
            List<GHNOrderLogDTO> logs = ghnOrderDetailDTO.getLog();
            if (logs != null || !logs.isEmpty()) {
                String ghnStatus = logs.getFirst().getStatus();
                OrderStatus orderStatus =
                        switch (ghnStatus) {
                            case "ready_to_pick" -> OrderStatus.PENDING_SHIPMENT;
                            case "picking", "money_collect_picking" -> OrderStatus.WAITING_FOR_PICKUP;
                            case "picked",
                                    "storing",
                                    "transporting",
                                    "sorting",
                                    "delivering",
                                    "money_collect_delivering",
                                    "return_transporting",
                                    "return_sorting",
                                    "returning" -> OrderStatus.IN_TRANSIT;
                            case "delivered" -> OrderStatus.DELIVERED;
                            case "delivery_fail", "return_fail" -> OrderStatus.DELIVERY_FAIL;
                            case "waiting_to_return", "return" -> OrderStatus.UNPAID;
                            case "cancel", "returned", "exception", "damage", "lost" -> OrderStatus.CANCELLED;
                            default -> null;
                        };
                ;

                if (orderStatus != null && !order.getOrderStatus().equals(orderStatus)) {
                    order.setOrderStatus(orderStatus);
                    orderDomainRepository.save(order);
                    StringBuilder title = new StringBuilder();
                    StringBuffer body = new StringBuffer();
                    if (orderStatus == OrderStatus.DELIVERED) {
                        title.append("Đơn hàng ").append(order.getOrderCode());
                        body.append("Đơn hàng đã được giao thành công");
                    } else if (orderStatus == OrderStatus.CANCELLED) {
                        title.append("Đơn hàng ").append(order.getOrderCode());
                        body.append("Đơn hàng đã bị hủy");
                    } else if (orderStatus == OrderStatus.DELIVERY_FAIL) {
                        title.append("Đơn hàng ").append(order.getOrderCode());
                        body.append("Đơn hàng  đã giao không thành công");
                    } else if (orderStatus == OrderStatus.WAITING_FOR_PICKUP) {
                        title.append("Đơn hàng ").append(order.getOrderCode());
                        body.append("Đơn hàng ").append(order.getOrderCode()).append(" đang chuẩn bị được lấy hàng");
                    }

                    PushNotificationEvent pushNotificationEvent = PushNotificationEvent.builder()
                            .title(title.toString())
                            .body(body.toString())
                            .userId(order.getUserId())
                            .data(Map.of("orderCode", order.getOrderCode()))
                            .build();
                    notiEventRabbitMQService.publishNotiPushEvent(pushNotificationEvent);

                    if (orderStatus == OrderStatus.DELIVERED) {
                        ProcessCashbackEvent processCashbackEvent = ProcessCashbackEvent.builder()
                                .userId(order.getUserId())
                                .orderId(order.getId())
                                .orderAmount(order.getTotalPrice())
                                .type(CashbackTransactionType.EARNED)
                                .build();

                        cashbackEventRabbitMQService.publishProcessCashbackEvent(processCashbackEvent);
                    }
                }
            }
        });
    }
}
