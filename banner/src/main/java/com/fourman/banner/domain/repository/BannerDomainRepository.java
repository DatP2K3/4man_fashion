package com.fourman.banner.domain.repository;

import java.util.List;
import java.util.UUID;

import com.fourman.banner.domain.Banner;
import com.fourman.common.repository.DomainRepository;

public interface BannerDomainRepository extends DomainRepository<Banner, UUID> {
    List<Banner> getAll();
}
