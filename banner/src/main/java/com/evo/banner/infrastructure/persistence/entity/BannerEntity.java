package com.evo.banner.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.evo.banner.infrastructure.support.enums.BannerType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "banners")
public class BannerEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "file_id")
    private UUID fileId;

    @Column(name = "position")
    private int position;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private BannerType type;

    @Column(name = "deleted")
    private Boolean deleted;
}
