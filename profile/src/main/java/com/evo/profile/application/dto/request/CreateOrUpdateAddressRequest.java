package com.evo.profile.application.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateAddressRequest {
    private UUID id;

    @NotBlank
    private String recipientName;

    @NotBlank
    @Pattern(regexp = "^(0|\\+84)[0-9]{9,10}$")
    private String phoneNumber;

    @NotBlank
    private String addressLine1;

    private String addressLine2;

    @NotBlank
    private String ward;

    @NotBlank
    private String district;

    @NotBlank
    private String wardCode;

    @NotBlank
    private String districtId;

    @NotBlank
    private String city;

    private Boolean defaultAddress;
    private UUID profileId;
}
