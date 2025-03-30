package com.evo.profile.application.service.impl.command;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.SyncUserEvent;
import com.evo.common.dto.request.SyncUserRequest;
import com.evo.common.enums.KafkaTopic;
import com.evo.common.enums.SyncUserType;
import com.evo.profile.application.dto.mapper.ProfileDTOMapper;
import com.evo.profile.application.dto.mapper.ShippingAddressDTOMapper;
import com.evo.profile.application.dto.request.CreateOrUpdateProfileRequest;
import com.evo.profile.application.dto.response.ProfileDTO;
import com.evo.profile.application.dto.response.ShippingAddressDTO;
import com.evo.profile.application.mapper.CommandMapper;
import com.evo.profile.application.mapper.SyncMapper;
import com.evo.profile.application.service.MembershipTierService;
import com.evo.profile.application.service.ProfileCommandService;
import com.evo.profile.domain.Profile;
import com.evo.profile.domain.ShippingAddress;
import com.evo.profile.domain.command.CreateOrUpdateProfileCmd;
import com.evo.profile.domain.command.CreateOrUpdateShippingAddressCmd;
import com.evo.profile.domain.repository.ProfileDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileDomainRepository profileDomainRepository;
    private final MembershipTierService membershipTierService;
    private final CommandMapper commandMapper;
    private final ShippingAddressDTOMapper shippingAddressDTOMapper;
    private final ProfileDTOMapper profileDTOMapper;
    private final SyncMapper syncMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void syncCreate(CreateOrUpdateProfileRequest createOrUpdateProfileRequest) {
        CreateOrUpdateProfileCmd cmd = commandMapper.from(createOrUpdateProfileRequest);
        UUID membershipTierId = membershipTierService.getDefaultMembershipTierId();
        cmd.setMembershipTierId(membershipTierId);
        Profile profile = new Profile(cmd);
        profileDomainRepository.save(profile);
    }

    @Override
    public ProfileDTO updateProfile(CreateOrUpdateProfileRequest updateProfileRequest) {
        CreateOrUpdateProfileCmd cmd = commandMapper.from(updateProfileRequest);
        Profile profile = profileDomainRepository.getById(cmd.getId());
        profile.update(cmd);

        SyncUserRequest syncUserRequest = syncMapper.from(profile);
        SyncUserEvent syncUserEvent = SyncUserEvent.builder()
                .syncUserType(SyncUserType.USER_UPDATED)
                .syncUserRequest(syncUserRequest)
                .build();
        kafkaTemplate.send(KafkaTopic.SYNC_USER_PROFILE_GROUP.getTopicName(), syncUserEvent);

        return profileDTOMapper.domainModelToDTO(profileDomainRepository.save(profile));
    }

    @Override
    public void delete(UUID id, boolean deleted) {}

    @Override
    public ShippingAddressDTO createShippingAddress(CreateOrUpdateProfileRequest request) {
        CreateOrUpdateShippingAddressCmd cmd = commandMapper.fromCreateOrUpdateProfileRequest(request);
        Profile profile = profileDomainRepository.getById(cmd.getUserId());
        ShippingAddress shippingAddress = profile.addShippingAddress(cmd);
        profileDomainRepository.save(profile);
        return shippingAddressDTOMapper.domainModelToDTO(shippingAddress);
    }

    @Override
    public ShippingAddressDTO updateShippingAddress(CreateOrUpdateProfileRequest request) {
        CreateOrUpdateShippingAddressCmd cmd = commandMapper.fromCreateOrUpdateProfileRequest(request);
        Profile profile = profileDomainRepository.getById(cmd.getUserId());
        ShippingAddress shippingAddress = profile.updateShippingAddress(cmd);
        profileDomainRepository.save(profile);
        return shippingAddressDTOMapper.domainModelToDTO(shippingAddress);
    }

    @Override
    public void syncUpdate(CreateOrUpdateProfileRequest request) {
        CreateOrUpdateProfileCmd cmd = commandMapper.from(request);
        Profile profile = profileDomainRepository.getById(cmd.getId());
        profile.update(cmd);
        profileDomainRepository.save(profile);
    }
}
