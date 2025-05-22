package com.evo.location.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDTO {
    private int id;
    private String name;
    private int provinceId;
    private String code;
    private Boolean enabled;
}
