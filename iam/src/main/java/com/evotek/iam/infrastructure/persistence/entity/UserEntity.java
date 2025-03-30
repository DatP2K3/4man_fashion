package com.evotek.iam.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.evo.common.entity.AuditEntity;

import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class UserEntity extends AuditEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "provider_id", unique = true)
    private String providerId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "locked", nullable = false)
    private boolean locked = false;

    @Column(name = "provider")
    private String provider;

    @Column(name = "two_factor_enabled")
    private Boolean twoFactorEnabled;
}
