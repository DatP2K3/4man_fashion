package com.evo.banner.domain.repository;

import java.util.List;
import java.util.UUID;

import com.evo.banner.domain.Banner;
import com.evo.common.repository.DomainRepository;

public interface BannerDomainRepository extends DomainRepository<Banner, UUID> {
    List<Banner> getAll();
}
