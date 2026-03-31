package com.fourman.notification.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fourman.common.entity.AuditEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "notification_deliveries")
public class NotificationDeliveryEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "notification_id")
    private UUID notificationId;

    @Column(name = "device_registration_id")
    private UUID deviceRegistrationId;

    @Column(name = "read")
    private Boolean read;

    @CreatedDate
    @Column(name = "send_at")
    private Instant sendAt;

    @LastModifiedDate
    @Column(name = "seen_at")
    private Instant seenAt;
}
