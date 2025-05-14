package com.evo.order.application.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.evo.common.enums.OrderStatus;
import com.evo.order.application.dto.request.GetGHNOrderDetailRequest;
import com.evo.order.application.dto.response.GHNOrderDetailDTO;
import com.evo.order.domain.Order;
import com.evo.order.domain.repository.OrderDomainRepository;
import com.evo.order.infrastructure.adapter.ghn.client.GHNClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CronJobService {
    private final OrderDomainRepository orderDomainRepository;
    private final GHNClient ghnClient;

    @Scheduled(cron = "0 */3 * * * *")
    public void syncOrderStatus() {
        List<OrderStatus> orderStatuses =
                Arrays.asList(OrderStatus.PENDING_SHIPMENT, OrderStatus.WAITING_FOR_PICKUP, OrderStatus.DELIVERED);
        List<Order> orders = orderDomainRepository.getAllOrderWithStatusIn(orderStatuses);
        orders.stream().filter(order -> order.getGHNOrderCode() != null).forEach(order -> {
            GetGHNOrderDetailRequest getGHNFeeRequest = new GetGHNOrderDetailRequest(order.getGHNOrderCode());
            GHNOrderDetailDTO ghnOrderDetailDTO =
                    ghnClient.getOrderDetail(getGHNFeeRequest).getData();
            String ghnStatus = ghnOrderDetailDTO.getLog().getFirst().getStatus();
            if (ghnStatus != null) {
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

                if (orderStatus != null) {
                    order.setOrderStatus(orderStatus);
                    orderDomainRepository.save(order);
                }
            }
        });
    }
}
