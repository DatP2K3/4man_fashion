package com.evo.profile.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.evo.common.enums.CashbackTransactionType;
import com.evo.profile.domain.command.CreateOrUpdateShippingAddressCmd;
import com.evo.profile.domain.command.UpdateProfileInfoCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Profile {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dob;
    private UUID avatarFileId;
    private UUID membershipTierId;
    private List<ShippingAddress> listShippingAddress;
    private UserWallet userWallet;
    private boolean deleted;

    public Profile(UpdateProfileInfoCmd cmd) {
        if (cmd.getId() != null) {
            this.id = cmd.getId();
        }

        if (cmd.getUsername() != null) {
            this.username = cmd.getUsername();
        }

        if (cmd.getEmail() != null) {
            this.email = cmd.getEmail();
        }
        this.membershipTierId = cmd.getMembershipTierId();
        this.userWallet = new UserWallet(cmd.getId());
    }

    public void update(UpdateProfileInfoCmd updateProfileCmd) {
        if (updateProfileCmd.getEmail() != null) {
            this.email = updateProfileCmd.getEmail();
        }
        if (updateProfileCmd.getFirstName() != null) {
            this.firstName = updateProfileCmd.getFirstName();
        }
        if (updateProfileCmd.getLastName() != null) {
            this.lastName = updateProfileCmd.getLastName();
        }
        if (updateProfileCmd.getPhoneNumber() != null) {
            this.phoneNumber = updateProfileCmd.getPhoneNumber();
        }
        if (updateProfileCmd.getDob() != null) {
            this.dob = updateProfileCmd.getDob();
        }
        if (updateProfileCmd.getMembershipTierId() != null) {
            this.membershipTierId = updateProfileCmd.getMembershipTierId();
        }
    }

    public void addShippingAddress(CreateOrUpdateShippingAddressCmd cmd) {
        ShippingAddress newShippingAddress = new ShippingAddress(cmd);
        if (this.listShippingAddress == null) {
            this.listShippingAddress = new ArrayList<>();
        }
        if (newShippingAddress.getDefaultAddress()) {
            for (ShippingAddress address : this.listShippingAddress) {
                address.setDefaultAddress(false);
            }
            newShippingAddress.setDefaultAddress(true);
        }

        this.listShippingAddress.add(newShippingAddress);
    }

    public void updateShippingAddress(CreateOrUpdateShippingAddressCmd cmd) {
        if (cmd.getDefaultAddress()) {
            for (ShippingAddress shippingAddress : this.listShippingAddress) {
                shippingAddress.setDefaultAddress(false);
                processUpdateShippingAddress(shippingAddress, cmd);
            }
        } else {
            for (ShippingAddress shippingAddress : this.listShippingAddress) {
                processUpdateShippingAddress(shippingAddress, cmd);
            }
        }
    }

    private void processUpdateShippingAddress(ShippingAddress shippingAddress, CreateOrUpdateShippingAddressCmd cmd) {
        if (shippingAddress.getId().equals(cmd.getId())) {
            shippingAddress.setRecipientName(cmd.getRecipientName());
            shippingAddress.setPhoneNumber(cmd.getPhoneNumber());
            shippingAddress.setAddressLine1(cmd.getAddressLine1());
            shippingAddress.setAddressLine2(cmd.getAddressLine2());
            shippingAddress.setWard(cmd.getWard());
            shippingAddress.setDistrict(cmd.getDistrict());
            shippingAddress.setWardCode(cmd.getWardCode());
            shippingAddress.setDistrictId(cmd.getDistrictId());
            shippingAddress.setCity(cmd.getCity());
        }
    }

    public void updateUserWallet(Long amount, CashbackTransactionType type) {
        if (type == CashbackTransactionType.EARNED) {
            this.userWallet.plusCashbackBalance(amount);
        } else {
            this.userWallet.minusCashbackBalance(amount);
        }
    }
}
