package com.evo.order.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetGHNOrderDetailRequest {
    @JsonProperty("order_code")
    private String orderCode;
}
