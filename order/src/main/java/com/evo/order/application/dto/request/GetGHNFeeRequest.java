package com.evo.order.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetGHNFeeRequest {
    @Builder.Default
    @JsonProperty("service_type_id")
    private int ServiceTypeId = 2;

    @JsonProperty("from_district_id")
    private int FromDistrictId;

    @JsonProperty("from_ward_code")
    private String FromWardCode;

    @JsonProperty("to_district_id")
    private int ToDistrictId;

    @JsonProperty("to_ward_code")
    private String ToWardCode;

    @JsonProperty("length")
    private int Length;

    @JsonProperty("width")
    private int Width;

    @JsonProperty("height")
    private int Height;

    @JsonProperty("weight")
    private int Weight;

    @JsonProperty("insurance_value")
    private Long InsuranceValue;

    @JsonProperty("coupon")
    private String Coupon;
}
