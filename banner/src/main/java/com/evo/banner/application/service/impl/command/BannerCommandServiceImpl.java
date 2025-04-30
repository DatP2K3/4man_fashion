package com.evo.banner.application.service.impl.command;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.evo.banner.application.dto.mapper.BannerDTOMapper;
import com.evo.banner.application.dto.request.CreateBannerRequest;
import com.evo.banner.application.dto.response.BannerDTO;
import com.evo.banner.application.mapper.CommandMapper;
import com.evo.banner.application.service.BannerCommandService;
import com.evo.banner.domain.Banner;
import com.evo.banner.domain.command.CreateBannerCmd;
import com.evo.banner.domain.repository.BannerDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BannerCommandServiceImpl implements BannerCommandService {
    private final CommandMapper commandMapper;
    private final BannerDTOMapper bannerDTOMapper;
    private final BannerDomainRepository bannerDomainRepository;

    @Override
    public BannerDTO createBanner(CreateBannerRequest request) {
        CreateBannerCmd createBannerCmd = commandMapper.from(request);
        Banner banner = new Banner(createBannerCmd);
        banner = bannerDomainRepository.save(banner);
        return bannerDTOMapper.domainModelToDTO(bannerDomainRepository.save(banner));
    }

    @Override
    public void deleteBanner(UUID id) {
        Banner banner = bannerDomainRepository.getById(id);
        banner.setDeleted(true);
        bannerDomainRepository.save(banner);
    }
}
