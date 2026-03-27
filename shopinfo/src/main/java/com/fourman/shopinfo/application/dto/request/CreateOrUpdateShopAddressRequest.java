package com.fourman.shopinfo.application.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.fourman.common.enums.ShopAddressType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrUpdateShopAddressRequest {
    private UUID id;

    @NotBlank
    private String shopName;

    @NotBlank
    @Pattern(regexp = "^(0|\\+84)[0-9]{9,10}$")
    private String phoneNumber;

    @NotBlank
    private String addressLine1;

    private String addressLine2;

    @NotBlank
    private String ward;

    @NotBlank
    private String wardCode;

    @NotBlank
    private String district;

    @NotBlank
    private String districtId;

    @NotBlank
    private String city;

    @NotNull
    private ShopAddressType type;
}
