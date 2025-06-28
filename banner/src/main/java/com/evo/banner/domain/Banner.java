package com.evo.banner.domain;

import java.util.UUID;

import com.evo.banner.domain.command.CreateBannerCmd;
import com.evo.banner.infrastructure.support.enums.BannerType;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Banner {
    private UUID id;
    private String title;
    private UUID fileId;
    private int position;
    private BannerType type;
    private Boolean deleted;

    public Banner(CreateBannerCmd createBannerCmd) {
        this.title = createBannerCmd.getTitle();
        this.fileId = createBannerCmd.getFileId();
        this.position = createBannerCmd.getPosition();
        this.type = createBannerCmd.getType();
        this.deleted = false;
    }
}
