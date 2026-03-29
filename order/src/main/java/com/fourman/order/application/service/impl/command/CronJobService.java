package com.fourman.order.application.service.impl.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fourman.common.dto.event.ProcessCashbackEvent;
import com.fourman.common.dto.event.PushNotificationEvent;
import com.fourman.common.enums.CashbackTransactionType;
import com.fourman.common.enums.OrderStatus;
import com.fourman.order.application.dto.request.GetGHNOrderDetailRequest;
import com.fourman.order.application.dto.response.GHNOrderDetailDTO;
import com.fourman.order.application.dto.response.GHNOrderLogDTO;
import com.fourman.order.domain.Order;
import com.fourman.order.domain.repository.OrderDomainRepository;
import com.fourman.order.infrastructure.adapter.ghn.client.GHNClient;
import com.fourman.order.infrastructure.adapter.rabbitmq.CashbackEventRabbitMQService;
import com.fourman.order.infrastructure.adapter.rabbitmq.NotiEventRabbitMQService;

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
        List<Order> updatedOrders = new ArrayList<>();

        orders.stream().filter(order -> order.getGhnOrderCode() != null).forEach(order -> {
            GetGHNOrderDetailRequest getGHNFeeRequest = new GetGHNOrderDetailRequest(order.getGhnOrderCode());
            GHNOrderDetailDTO ghnOrderDetailDTO =
                    ghnClient.getOrderDetail(getGHNFeeRequest).getData();
            List<GHNOrderLogDTO> logs = ghnOrderDetailDTO.getLog();
            if (logs != null && !logs.isEmpty()) {
                String ghnStatus = logs.getFirst().getStatus();
                OrderStatus orderStatus = mapGhnStatus(ghnStatus);

                if (orderStatus != null && !order.getOrderStatus().equals(orderStatus)) {
                    order.updateStatusFromTracking(orderStatus);
                    updatedOrders.add(order);

                    String title = "Đơn hàng " + order.getOrderCode();
                    String body = switch (orderStatus) {
                        case DELIVERED -> "Đơn hàng đã được giao thành công";
                        case CANCELLED -> "Đơn hàng đã bị hủy";
                        case DELIVERY_FAIL -> "Đơn hàng đã giao không thành công";
                        case WAITING_FOR_PICKUP -> "Đơn hàng " + order.getOrderCode() + " đang chuẩn bị được lấy hàng";
                        default -> null;
                    };

                    if (body != null) {
                        PushNotificationEvent pushNotificationEvent = PushNotificationEvent.builder()
                                .title(title)
                                .body(body)
                                .userId(order.getUserId())
                                .data(Map.of("orderCode", order.getOrderCode()))
                                .build();
                        notiEventRabbitMQService.publishNotiPushEvent(pushNotificationEvent);
                    }

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

        if (!updatedOrders.isEmpty()) {
            orderDomainRepository.saveAll(updatedOrders);
        }
    }

    private OrderStatus mapGhnStatus(String ghnStatus) {
        return switch (ghnStatus) {
            case "ready_to_pick" -> OrderStatus.PENDING_SHIPMENT;
            case "picking", "money_collect_picking" -> OrderStatus.WAITING_FOR_PICKUP;
            case "picked", "storing", "transporting", "sorting", "delivering",
                    "money_collect_delivering", "return_transporting", "return_sorting",
                    "returning" -> OrderStatus.IN_TRANSIT;
            case "delivered" -> OrderStatus.DELIVERED;
            case "delivery_fail", "return_fail" -> OrderStatus.DELIVERY_FAIL;
            case "waiting_to_return", "return" -> OrderStatus.UNPAID;
            case "cancel", "returned", "exception", "damage", "lost" -> OrderStatus.CANCELLED;
            default -> null;
        };
    }
}
