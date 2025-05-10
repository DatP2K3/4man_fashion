package com.evo.order.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateGHNOrderRequest {
    @JsonProperty("from_name")
    private String fromName;

    @JsonProperty("from_phone")
    private String fromPhone;

    @JsonProperty("from_address")
    private String fromAddress;

    @JsonProperty("from_ward_name")
    private String fromWardName;

    @JsonProperty("from_district_name")
    private String fromDistrictName;

    @JsonProperty("from_province_name")
    private String fromProvinceName;

    @JsonProperty("to_name")
    private String toName;

    @JsonProperty("to_phone")
    private String toPhone;

    @JsonProperty("to_address")
    private String toAddress;

    @JsonProperty("to_ward_name")
    private String toWardName;

    @JsonProperty("to_district_name")
    private String toDistrictName;

    @JsonProperty("to_province_name")
    private String toProvinceName;

    @JsonProperty("return_phone")
    private String returnPhone;

    @JsonProperty("return_address")
    private String returnAddress;

    @JsonProperty("return_district_name")
    private String returnDistrictName;

    @JsonProperty("return_ward_name")
    private String returnWardName;

    @JsonProperty("return_province_name")
    private String returnProvinceName;

    @JsonProperty("client_order_code")
    private String clientOrderCode;

    @JsonProperty("cod_amount")
    private Long codAmount;

    @JsonProperty("content")
    private String content;

    @JsonProperty("weight")
    private Integer weight;

    @JsonProperty("length")
    private Integer length;

    @JsonProperty("width")
    private Integer width;

    @JsonProperty("height")
    private Integer height;

    @JsonProperty("pick_station_id")
    private Integer pickStationId;

    @JsonProperty("insurance_value")
    private Integer insuranceValue;

    @JsonProperty("coupon")
    private String coupon;

    @JsonProperty("service_type_id")
    private Integer serviceTypeId;

    @JsonProperty("payment_type_id")
    private Integer paymentTypeId;

    @JsonProperty("note")
    private String note;

    @JsonProperty("required_note")
    private String requiredNote;


}