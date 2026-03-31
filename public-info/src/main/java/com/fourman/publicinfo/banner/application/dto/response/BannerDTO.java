package com.fourman.publicinfo.banner.application.dto.response;

import java.util.UUID;

import com.fourman.publicinfo.banner.domain.enums.BannerType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerDTO {
    private UUID id;
    private String title;
    private UUID fileId;
    private int position;
    private BannerType type;
    private Boolean deleted;
}
