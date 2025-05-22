package com.evo.location.infrastructure.persistence.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ghn_districts")
public class DistrictEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "province_id")
    private int provinceId;

    @Column(name = "code")
    private String code;

    @Column(name = "is_enabled")
    private Boolean enabled;
}
