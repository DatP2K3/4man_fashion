package com.evo.profile.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.evo.common.entity.AuditEntity;
import jakarta.persistence.*;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@Table(name = "profiles")
public class ProfileEntity extends AuditEntity {
    @Id
    @Column(name = "id")
    private UUID id; // dùng chung id với bảng user,

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "avatar_file_id")
    private UUID avatarFileId;

    @Column(name = "membership_tier_id")
    private UUID membershipTierId;

    @Column(name = "deleted")
    private Boolean deleted;
}
