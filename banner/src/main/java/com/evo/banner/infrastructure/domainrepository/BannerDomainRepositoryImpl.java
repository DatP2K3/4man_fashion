package com.evo.banner.infrastructure.domainrepository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.evo.banner.domain.Banner;
import com.evo.banner.domain.repository.BannerDomainRepository;
import com.evo.banner.infrastructure.persistence.entity.BannerEntity;
import com.evo.banner.infrastructure.persistence.mapper.BannerEntityMapper;
import com.evo.banner.infrastructure.persistence.repository.BannerEntityRepository;
import com.evo.banner.infrastructure.support.exception.AppErrorCode;
import com.evo.banner.infrastructure.support.exception.AppException;
import com.evo.common.repository.AbstractDomainRepository;

@Repository
public class BannerDomainRepositoryImpl extends AbstractDomainRepository<Banner, BannerEntity, UUID>
        implements BannerDomainRepository {
    private final BannerEntityMapper bannerEntityMapper;
    private final BannerEntityRepository bannerEntityRepository;

    public BannerDomainRepositoryImpl(
            BannerEntityMapper bannerEntityMapper, BannerEntityRepository bannerEntityRepository) {
        super(bannerEntityRepository, bannerEntityMapper);
        this.bannerEntityMapper = bannerEntityMapper;
        this.bannerEntityRepository = bannerEntityRepository;
    }

    @Override
    public Banner getById(UUID uuid) {
        BannerEntity bannerEntity = bannerEntityRepository
                .findById(uuid)
                .orElseThrow(() -> new AppException(AppErrorCode.BANNER_NOT_FOUND));
        return bannerEntityMapper.toDomainModel(bannerEntity);
    }

    @Override
    public List<Banner> getAll() {
        List<BannerEntity> bannerEntities = bannerEntityRepository.findByDeletedFalse();
        return bannerEntityMapper.toDomainModelList(bannerEntities);
    }
}
