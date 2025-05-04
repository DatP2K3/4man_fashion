package com.evo.order.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GHNFeeDTO {
    @JsonProperty("total")
    private int total;

    @JsonProperty("service_fee")
    private int serviceFee;

    @JsonProperty("insurance_fee")
    private int insuranceFee;

    @JsonProperty("pick_station_fee")
    private int pickStationFee;

    @JsonProperty("coupon_value")
    private int couponValue;

    @JsonProperty("r2s_fee")
    private int r2sFee;

    @JsonProperty("document_return")
    private int documentReturn;

    @JsonProperty("double_check")
    private int doubleCheck;

    @JsonProperty("cod_fee")
    private int codFee;

    @JsonProperty("pick_remote_areas_fee")
    private int pickRemoteAreasFee;

    @JsonProperty("deliver_remote_areas_fee")
    private int deliverRemoteAreasFee;

    @JsonProperty("cod_failed_fee")
    private int codFailedFee;
}
