package com.evo.profile.infrastructure.domainrepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.evo.profile.domain.query.SearchProfileQuery;
import org.springframework.stereotype.Repository;

import com.evo.common.repository.AbstractDomainRepository;
import com.evo.profile.domain.Profile;
import com.evo.profile.domain.ShippingAddress;
import com.evo.profile.domain.UserWallet;
import com.evo.profile.domain.repository.ProfileDomainRepository;
import com.evo.profile.infrastructure.persistence.entity.ProfileEntity;
import com.evo.profile.infrastructure.persistence.entity.ShippingAddressEntity;
import com.evo.profile.infrastructure.persistence.entity.UserWalletEntity;
import com.evo.profile.infrastructure.persistence.mapper.ProfileEntityMapper;
import com.evo.profile.infrastructure.persistence.mapper.ShippingAdressEntityMapper;
import com.evo.profile.infrastructure.persistence.mapper.UserWalletEntityMapper;
import com.evo.profile.infrastructure.persistence.repository.ProfileEntityRepository;
import com.evo.profile.infrastructure.persistence.repository.ShippingAddressEntityRepository;
import com.evo.profile.infrastructure.persistence.repository.UserWalletEntityRepository;
import com.evo.profile.infrastructure.support.exception.AppErrorCode;
import com.evo.profile.infrastructure.support.exception.AppException;

@Repository
public class ProfileDomainRepositoryImpl extends AbstractDomainRepository<Profile, ProfileEntity, UUID>
        implements ProfileDomainRepository {
    private final ProfileEntityMapper profileEntityMapper;
    private final ProfileEntityRepository profileEntityRepository;
    private final UserWalletEntityMapper userWalletEntityMapper;
    private final UserWalletEntityRepository userWalletEntityRepository;
    private final ShippingAdressEntityMapper shippingAdressEntityMapper;
    private final ShippingAddressEntityRepository shippingAddressEntityRepository;

    public ProfileDomainRepositoryImpl(
            ProfileEntityRepository profileEntityRepository,
            ProfileEntityMapper profileEntityMapper,
            UserWalletEntityMapper userWalletEntityMapper,
            UserWalletEntityRepository userWalletEntityRepository,
            ShippingAdressEntityMapper shippingAdressEntityMapper,
            ShippingAddressEntityRepository shippingAddressEntityRepository) {
        super(profileEntityRepository, profileEntityMapper);
        this.profileEntityRepository = profileEntityRepository;
        this.profileEntityMapper = profileEntityMapper;
        this.userWalletEntityMapper = userWalletEntityMapper;
        this.userWalletEntityRepository = userWalletEntityRepository;
        this.shippingAddressEntityRepository = shippingAddressEntityRepository;
        this.shippingAdressEntityMapper = shippingAdressEntityMapper;
    }

    @Override
    protected List<Profile> enrichList(List<Profile> profiles) {
        if (profiles.isEmpty()) return profiles;

        List<UUID> profileIds = profiles.stream().map(Profile::getId).toList(); // userId === profileId

        Map<UUID, List<ShippingAddress>> shippingAddressMap =
                shippingAddressEntityRepository.findByProfileIdIn(profileIds).stream()
                        .collect(Collectors.groupingBy(
                                ShippingAddressEntity::getProfileId,
                                Collectors.mapping(shippingAdressEntityMapper::toDomainModel, Collectors.toList())));

        Map<UUID, UserWallet> userWalletMap = userWalletEntityRepository.findByProfileIdIn(profileIds).stream()
                .collect(Collectors.toMap(UserWalletEntity::getProfileId, userWalletEntityMapper::toDomainModel));

        profiles.forEach(profile -> {
            profile.setListShippingAddress(shippingAddressMap.get(profile.getId()));
            profile.setUserWallet(userWalletMap.get(profile.getId()));
        });

        return profiles;
    }

    @Override
    public Profile save(Profile profile) {
        UserWallet userWallet = profile.getUserWallet();
        userWalletEntityRepository.save(userWalletEntityMapper.toEntity(userWallet));

        List<ShippingAddress> listShippingAddress = profile.getListShippingAddress();
        if (listShippingAddress != null) {
            shippingAddressEntityRepository.saveAll(shippingAdressEntityMapper.toEntityList(listShippingAddress));
        }

        return this.enrich(
                profileEntityMapper.toDomainModel(profileEntityRepository.save(profileEntityMapper.toEntity(profile))));
    }

    @Override
    public Profile getById(UUID profileId) {
        return this.enrich(profileEntityMapper.toDomainModel(profileEntityRepository
                .findById(profileId)
                .orElseThrow(() -> new AppException(AppErrorCode.PROFILE_NOT_FOUND))));
    }

    @Override
    public Profile getByIdOrNull(UUID profileId) {
        return this.enrich(profileEntityMapper.toDomainModel(
                profileEntityRepository.findById(profileId).orElse(null)));
    }

    @Override
    public ShippingAddress getDefaultShippingAddress() {
        return shippingAdressEntityMapper.toDomainModel(
                shippingAddressEntityRepository.findByDefaultAddressTrue().orElse(null));
    }

    @Override
    public List<Profile> search(SearchProfileQuery searchUserQuery) {
        List<ProfileEntity> profileEntities = profileEntityRepository.search(searchUserQuery);
        return this.enrichList(profileEntityMapper.toDomainModelList(profileEntities));
    }

    @Override
    public Long count(SearchProfileQuery searchUserQuery) {
        return profileEntityRepository.count(searchUserQuery);
    }
}
