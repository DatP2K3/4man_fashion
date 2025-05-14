package com.evo.profile.application.dto.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.evo.common.dto.response.ProfileDTO;
import com.evo.profile.application.dto.mapper.ProfileDTOMapper;
import com.evo.profile.application.dto.mapper.ShippingAddressDTOMapper;
import com.evo.profile.domain.MembershipTier;
import com.evo.profile.domain.Profile;
import com.evo.profile.domain.repository.MembershipTierDomainRepository;
import com.evo.profile.infrastructure.persistence.entity.ProfileEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileDTOMapperImpl implements ProfileDTOMapper {
    private final MembershipTierDomainRepository membershipTierDomainRepository;
    private final ShippingAddressDTOMapper shippingAddressDTOMapper;

    @Override
    public ProfileDTO domainModelToDTO(Profile profile) {
        if (profile == null) {
            return null;
        }

        ProfileDTO dto = new ProfileDTO();
        dto.setId(profile.getId());
        dto.setEmail(profile.getEmail());
        dto.setFirstName(profile.getFirstName());
        dto.setLastName(profile.getLastName());
        dto.setPhoneNumber(profile.getPhoneNumber());
        dto.setDob(profile.getDob());
        dto.setAvatarFileId(profile.getAvatarFileId());

        // Xử lý userWallet
        if (profile.getUserWallet() != null) {
            dto.setTotalPoints(profile.getUserWallet().getTotalPoints());
            dto.setTotalCoins(profile.getUserWallet().getTotalCoins());
        }

        // Xử lý địa chỉ giao hàng
        if (profile.getListShippingAddress() != null) {
            dto.setListShippingAddress(profile.getListShippingAddress().stream()
                    .map(shippingAddressDTOMapper::domainModelToDTO)
                    .toList());
        }

        // Xử lý thông tin membership tier
        if (profile.getMembershipTierId() != null) {
            MembershipTier currentTier = membershipTierDomainRepository.getById(profile.getMembershipTierId());
            dto.setMembershipTierName(currentTier.getName());

            // Tìm tier tiếp theo
            MembershipTier nextTier = membershipTierDomainRepository.getNextTier(currentTier.getMinPoints());
            if (nextTier != null) {
                dto.setNextMembershipTierName(nextTier.getName());
                // Tính điểm cần để lên cấp tiếp theo
                int currentPoints = profile.getUserWallet() != null
                        ? profile.getUserWallet().getTotalPoints().intValue()
                        : 0;
                dto.setPointsToNextLevel(nextTier.getMinPoints() - currentPoints);

                // Tính phần trăm để lên cấp tiếp theo
                if (nextTier.getMinPoints() > 0) {
                    dto.setPercent((currentPoints * 100) / nextTier.getMinPoints());
                } else {
                    dto.setPercent(0);
                }
            }
        }

        return dto;
    }

    @Override
    public ProfileDTO entityToDTO(ProfileEntity entity) {
        return null;
    }

    @Override
    public List<ProfileDTO> entitiesToDTOs(List<ProfileEntity> entities) {
        return List.of();
    }

    @Override
    public Profile dtoToDomainModel(ProfileDTO dto) {
        if (dto == null) {
            return null;
        }

        Profile profile = new Profile();
        profile.setId(dto.getId());
        profile.setEmail(dto.getEmail());
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setDob(dto.getDob());
        profile.setAvatarFileId(dto.getAvatarFileId());

        // Không thể set membershipTierId trực tiếp từ DTO vì DTO chỉ có tên
        // Không thể set listShippingAddress vì cần convert từ DTO sang Domain
        // Không thể set userWallet vì DTO chỉ có totalCoins

        return profile;
    }

    @Override
    public List<ProfileDTO> domainModelsToDTOs(List<Profile> models) {
        List<ProfileDTO> dtos = new ArrayList<>();
        for (Profile model : models) {
            dtos.add(domainModelToDTO(model));
        }
        return dtos;
    }
}
