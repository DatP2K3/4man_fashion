package com.evo.order.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelGHNOrderRequest {
    @JsonProperty("order_codes")
    private List<String> orderCodes;
}
