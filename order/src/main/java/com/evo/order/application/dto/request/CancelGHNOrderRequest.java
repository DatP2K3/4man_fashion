package com.evo.order.application.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelGHNOrderRequest {
    @JsonProperty("order_codes")
    private List<String> orderCodes;
}
