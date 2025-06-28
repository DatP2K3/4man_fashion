package com.evo.profile.application.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.evo.common.dto.response.DTOMapper;
import com.evo.common.dto.response.ProfileDTO;
import com.evo.profile.domain.Profile;
import com.evo.profile.infrastructure.persistence.entity.ProfileEntity;

@Mapper()
public interface ProfileDTOMapper extends DTOMapper<ProfileDTO, Profile, ProfileEntity> {

    @Mapping(target = "membershipTierName", ignore = true) // Sẽ xử lý riêng
    @Mapping(target = "nextMembershipTierName", ignore = true) // Sẽ xử lý riêng
    @Mapping(target = "pointsToNextLevel", ignore = true) // Sẽ xử lý riêng
    @Mapping(target = "percent", ignore = true) // Sẽ xử lý riêng
    @Mapping(target = "totalPoints", source = "userWallet.totalPoints")
    @Mapping(target = "cashbackBalance", source = "userWallet.cashbackBalance")
    ProfileDTO domainModelToDTO(Profile profile);
}
