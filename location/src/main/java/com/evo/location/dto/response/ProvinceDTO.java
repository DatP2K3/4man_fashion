package com.evo.location.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDTO {
    private int id;
    private String name;
    private String code;
    private Boolean enabled;
}
