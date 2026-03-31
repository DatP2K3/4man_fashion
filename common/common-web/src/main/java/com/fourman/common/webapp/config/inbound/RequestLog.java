package com.fourman.common.webapp.config.inbound;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

import org.hibernate.annotations.UuidGenerator;

import lombok.*;

@Entity
@Table(name = "request_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestLog {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "method", nullable = false, length = 10)
    private String method;

    @Column(name = "path", nullable = false, length = 512)
    private String path;

    @Column(name = "query_string", length = 2048)
    private String queryString;

    @Column(name = "client_ip", length = 45)
    private String clientIp;

    @Column(name = "client_os", length = 100)
    private String clientOs;

    @Column(name = "client_browser", length = 100)
    private String clientBrowser;

    @Column(name = "client_device", length = 50)
    private String clientDevice;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "request_body", columnDefinition = "TEXT")
    private String requestBody;

    @Column(name = "response_body", columnDefinition = "TEXT")
    private String responseBody;

    @Column(name = "request_at", nullable = false)
    private Instant requestAt;

    @Column(name = "response_at")
    private Instant responseAt;

    @Column(name = "duration_ms")
    private Long durationMs;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_by")
    private UUID createdBy;
}
