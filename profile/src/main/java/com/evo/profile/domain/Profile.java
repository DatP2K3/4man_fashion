package com.evo.profile.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.evo.profile.domain.command.CreateOrUpdateProfileCmd;
import com.evo.profile.domain.command.CreateOrUpdateShippingAddressCmd;

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

    public Profile(CreateOrUpdateProfileCmd syncUserCmd) {
        this.id = syncUserCmd.getId();
        this.email = syncUserCmd.getEmail();
        this.membershipTierId = syncUserCmd.getMembershipTierId();
        this.userWallet = new UserWallet(syncUserCmd.getId());
    }

    public void update(CreateOrUpdateProfileCmd updateProfileCmd) {
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

    public ShippingAddress addShippingAddress(CreateOrUpdateShippingAddressCmd cmd) {
        ShippingAddress shippingAddress = new ShippingAddress(cmd);
        if (this.listShippingAddress == null) {
            this.listShippingAddress = new ArrayList<>();
        }
        this.listShippingAddress.add(shippingAddress);
        return shippingAddress;
    }

    public ShippingAddress updateShippingAddress(CreateOrUpdateShippingAddressCmd cmd) {
        for (ShippingAddress shippingAddress : this.listShippingAddress) {
            if (shippingAddress.getId().equals(cmd.getId())) {
                shippingAddress.setRecipientName(cmd.getRecipientName());
                shippingAddress.setPhoneNumber(cmd.getPhoneNumber());
                shippingAddress.setAddressLine1(cmd.getAddressLine1());
                shippingAddress.setAddressLine2(cmd.getAddressLine2());
                shippingAddress.setWard(cmd.getWard());
                shippingAddress.setDistrict(cmd.getDistrict());
                shippingAddress.setCity(cmd.getCity());
                return shippingAddress;
            }
        }
        return null;
    }
}
