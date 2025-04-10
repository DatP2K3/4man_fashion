package com.evo.profile.application.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProfileDTO {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dob;
    private UUID avatarFileId;
    private String membershipTierName;
    private String nextMembershipTierName;
    private List<ShippingAddressDTO> listShippingAddress;
    private int pointsToNextLevel;
    private Long totalPoints;
    private Long totalCoins;
    private int percent;
}
