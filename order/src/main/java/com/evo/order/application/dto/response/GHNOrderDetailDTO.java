package com.evo.order.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GHNOrderDetailDTO {
    private List<GHNOrderLogDTO> log;
}
