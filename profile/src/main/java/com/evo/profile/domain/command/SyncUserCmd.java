package com.evo.profile.domain.command;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SyncUserCmd {
    private UUID id;
    private UUID providerId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UUID avatarFileId;
    private LocalDate dob;
    private String street;
    private String ward;
    private String district;
    private String city;
    private int yearsOfExperience;
    private String password;
    private boolean locked;
    private String provider;
    private String phoneNumber;
    private UUID membershipTierId;
    private List<UUID> roleIds;
}
