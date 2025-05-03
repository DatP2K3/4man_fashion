package com.evo.shopinfo.domain;

import com.evo.common.Auditor;
import com.evo.common.enums.ShopAddressType;
import com.evo.shopinfo.application.dto.response.ShopAddressDTO;
import com.evo.shopinfo.domain.command.CreateUpdateShopAddressCmd;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class ShopAddress extends Auditor {
    private UUID id;
    private String shopName;
    private String phoneNumber;
    private String addressLine1; // Địa chỉ cụ thể (Số nhà, tên đường)
    private String addressLine2; // Địa chỉ bổ sung (Tòa nhà, căn hộ)
    private String ward; // Phường/Xã
    private String wardCode; // Mã phường/xã
    private String district; // Quận/Huyện
    private String districtId; // Id quận/huyện
    private String city;
    private ShopAddressType type;

    public ShopAddress(CreateUpdateShopAddressCmd cmd) {
        this.shopName = cmd.getShopName();
        this.phoneNumber = cmd.getPhoneNumber();
        this.addressLine1 = cmd.getAddressLine1();
        this.addressLine2 = cmd.getAddressLine2();
        this.ward = cmd.getWard();
        this.wardCode = cmd.getWardCode();
        this.district = cmd.getDistrict();
        this.districtId = cmd.getDistrictId();
        this.city = cmd.getCity();
        this.type = cmd.getType();
    }

    public void update(CreateUpdateShopAddressCmd cmd) {
        if(cmd.getShopName() != null) {
            this.shopName = cmd.getShopName();
        }
        if(cmd.getPhoneNumber() != null) {
            this.phoneNumber = cmd.getPhoneNumber();
        }
        if(cmd.getAddressLine1() != null) {
            this.addressLine1 = cmd.getAddressLine1();
        }
        if(cmd.getAddressLine2() != null) {
            this.addressLine2 = cmd.getAddressLine2();
        }
        if(cmd.getWard() != null) {
            this.ward = cmd.getWard();
        }
        if(cmd.getWardCode() != null) {
            this.wardCode = cmd.getWardCode();
        }
        if(cmd.getDistrict() != null) {
            this.district = cmd.getDistrict();
        }
        if(cmd.getDistrictId() != null) {
            this.districtId = cmd.getDistrictId();
        }
        if(cmd.getCity() != null) {
            this.city = cmd.getCity();
        }
        if(cmd.getType() != null) {
            this.type = cmd.getType();
        }
    }
}
