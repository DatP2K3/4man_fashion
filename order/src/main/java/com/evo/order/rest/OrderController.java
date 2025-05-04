package com.evo.order.rest;

import com.evo.common.dto.response.ApiResponses;
import com.evo.order.application.dto.response.OrderFeeDTO;
import com.evo.order.application.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
    private final OrderQueryService orderQueryService;

    @GetMapping("caculate-fee/{toAddressId}")
    public ApiResponses<OrderFeeDTO> caculateFeeByAddressId(@PathVariable UUID toAddressId) {
        OrderFeeDTO orderFeeDTO = orderQueryService.caculateFeeByAddressId(toAddressId);
        return ApiResponses.<OrderFeeDTO>builder()
                .data(orderFeeDTO)
                .success(true)
                .code(200)
                .message("Get order fee successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
