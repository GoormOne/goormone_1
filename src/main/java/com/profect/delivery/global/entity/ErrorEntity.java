package com.profect.delivery.global.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_errors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorEntity {

    @Id
    @GeneratedValue
    @Column(name = "error_id", columnDefinition = "UUID")
    private UUID errorId;

    @Column(name = "user_id", length = 10, nullable = false)
    private String userId;

    @Column(name = "request_url", length = 255, nullable = false)
    private String requestUrl;

    @Column(name = "http_method", length = 10, nullable = false)
    private String httpMethod;

    @Column(name = "error_code", length = 20, nullable = false)
    private String errorCode;

    @Column(name = "error_message", columnDefinition = "text", nullable = false)
    private String errorMessage;

    @Column(name = "client_ip", length = 45, nullable = false)
    private String clientIp;

    @Column(name = "user_agent", columnDefinition = "text", nullable = false)
    private String userAgent;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


}
