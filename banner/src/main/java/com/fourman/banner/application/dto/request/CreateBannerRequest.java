package com.fourman.banner.application.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fourman.banner.infrastructure.support.enums.BannerType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBannerRequest {
    private UUID id;

    @NotBlank
    private String title;

    @NotNull
    private UUID fileId;

    @Min(0)
    private int position;

    @NotNull
    private BannerType type;

    private Boolean deleted;
}
