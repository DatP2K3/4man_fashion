package com.evo.banner.domain.command;

import java.util.UUID;

import com.evo.banner.infrastructure.support.enums.BannerType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBannerCmd {
    private UUID id;
    private String title;
    private UUID fileId;
    private int position;
    private BannerType type;
    private Boolean deleted;
}
