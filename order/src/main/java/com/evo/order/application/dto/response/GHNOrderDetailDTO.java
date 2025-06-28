package com.evo.order.application.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GHNOrderDetailDTO {
    private List<GHNOrderLogDTO> log;
}
