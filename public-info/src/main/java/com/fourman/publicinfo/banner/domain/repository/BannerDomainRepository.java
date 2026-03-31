package com.fourman.publicinfo.banner.domain.repository;

import java.util.List;
import java.util.UUID;

import com.fourman.publicinfo.banner.domain.Banner;
import com.fourman.common.repository.DomainRepository;

public interface BannerDomainRepository extends DomainRepository<Banner, UUID> {
    List<Banner> getAll();
}
