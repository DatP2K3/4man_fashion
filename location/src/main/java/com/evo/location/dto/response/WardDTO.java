package com.evo.location.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardDTO {
    private String wardCode;
    private String wardName;
    private int districtId;
}
