package com.evo.profile.application.service.impl.command;

import java.util.List;
import java.util.UUID;

import com.evo.profile.domain.UserWallet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.evo.common.dto.response.FileResponse;
import com.evo.common.dto.response.ProfileDTO;
import com.evo.profile.application.dto.mapper.ProfileDTOMapper;
import com.evo.profile.application.dto.request.CreateOrUpdateAddressRequest;
import com.evo.profile.application.dto.request.UpdateProfileInfoRequest;
import com.evo.profile.application.mapper.CommandMapper;
import com.evo.profile.application.service.MembershipTierCommandService;
import com.evo.profile.application.service.ProfileCommandService;
import com.evo.profile.domain.Profile;
import com.evo.profile.domain.command.CreateOrUpdateShippingAddressCmd;
import com.evo.profile.domain.command.UpdateProfileInfoCmd;
import com.evo.profile.domain.repository.ProfileDomainRepository;
import com.evo.profile.infrastructure.adapter.keycloak.KeycloakService;
import com.evo.profile.infrastructure.adapter.storage.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileDomainRepository profileDomainRepository;
    private final MembershipTierCommandService membershipTierCommandService;
    private final CommandMapper commandMapper;
    private final ProfileDTOMapper profileDTOMapper;
    private final FileService fileService;
    private final KeycloakService keycloakService;

    @Override
    public ProfileDTO getOrInitProfile() {
        var context = SecurityContextHolder.getContext();
        UUID profileId = UUID.fromString(context.getAuthentication().getName());
        Authentication authentication = context.getAuthentication();
        String email = null;
        String username = null;
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtToken.getToken();

            email = jwt.getClaimAsString("email");
            username = jwt.getClaimAsString("preferred_username");
        }

        Profile profile = profileDomainRepository.getByIdOrNull(profileId);
        if (profile != null) {
            if (!profile.getEmail().equals(email)) {
                profile.setEmail(email);
                profile = profileDomainRepository.save(profile);
            }
            return profileDTOMapper.domainModelToDTO(profile);
        }

        UUID membershipTierId = membershipTierCommandService.getDefaultMembershipTierId();
        UpdateProfileInfoCmd cmd = new UpdateProfileInfoCmd();
        cmd.setMembershipTierId(membershipTierId);
        cmd.setEmail(email);
        cmd.setId(profileId);
        profile = new Profile(cmd);
        profileDomainRepository.save(profile);
        return profileDTOMapper.domainModelToDTO(profile);
    }

    @Override
    public ProfileDTO updateProfile(UpdateProfileInfoRequest updateProfileInfoRequest) {
        UpdateProfileInfoCmd cmd = commandMapper.from(updateProfileInfoRequest);
        Profile profile = profileDomainRepository.getById(cmd.getId());
        profile.update(cmd);
        return profileDTOMapper.domainModelToDTO(profileDomainRepository.save(profile));
    }

    @Override
    public ProfileDTO changeAvatar(MultipartFile file) {
        var context = SecurityContextHolder.getContext();
        String profileId = context.getAuthentication().getName();
        FileResponse fileResponse = fileService
                .uploadFile(List.of(file), true, "Avatar for profileId " + profileId)
                .getFirst();

        Profile profile = profileDomainRepository.getById(UUID.fromString(profileId));
        profile.setAvatarFileId(fileResponse.getId());
        profileDomainRepository.save(profile);
        return profileDTOMapper.domainModelToDTO(profile);
    }

    @Override
    public void delete(UUID id, boolean deleted) {
        Profile profile = profileDomainRepository.getById(id);
        profile.setDeleted(true);
        profileDomainRepository.save(profile);
        // TODO: lock profile in keycloak
    }

    @Override
    public ProfileDTO createShippingAddress(CreateOrUpdateAddressRequest request) {
        CreateOrUpdateShippingAddressCmd cmd = commandMapper.fromCreateOrUpdateProfileRequest(request);
        Profile profile = profileDomainRepository.getById(cmd.getProfileId());
        profile.addShippingAddress(cmd);
        profileDomainRepository.save(profile);
        return profileDTOMapper.domainModelToDTO(profile);
    }

    @Override
    public ProfileDTO updateShippingAddress(CreateOrUpdateAddressRequest request) {
        CreateOrUpdateShippingAddressCmd cmd = commandMapper.fromCreateOrUpdateProfileRequest(request);
        Profile profile = profileDomainRepository.getById(cmd.getProfileId());
        profile.updateShippingAddress(cmd);
        profileDomainRepository.save(profile);
        return profileDTOMapper.domainModelToDTO(profile);
    }
}
