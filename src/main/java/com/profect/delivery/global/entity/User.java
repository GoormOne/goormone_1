package com.profect.delivery.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "p_users") // 클래스명과 테이블명이 다를 경우를 대비해 @Table 어노테이션을 사용하는 것이 좋습니다.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    private String  userId;

    @Column(name = "username", nullable = false, unique = true) // 예시: 제약조건 추가
    private String username;

    @Column(name = "password", nullable = false) // 예시: 제약조건 추가
    private String password;

    @Column(name = "name")
    private String name;

    @Temporal(TemporalType.DATE) // 날짜만 저장 (시간 제외)
    @Column(name = "birth")
    private Date birth;

    @Column(name = "email", unique = true) // 예시: 제약조건 추가
    private String email;

    @Enumerated(EnumType.STRING) // Enum의 이름을 DB에 저장 (ORDINAL(숫자)보다 권장)
    @Column(name = "role")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Role role;

    @Column(name = "is_public", columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isPublic=true;


    @Column(name = "is_banned")
    private boolean isBanned = false;

    @Column(name = "created_at", updatable = false) // 생성 시간은 업데이트되지 않도록 설정
    private Timestamp createdAt;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_rs")
    private String deletedRs;


    public void update(String name, String password, String email, Boolean isPublic,String updatedBy) {
        if (name != null) this.name = name;
        if (password != null) this.password = password;
        if (email != null) this.email = email;
        if (isPublic != null) this.isPublic = isPublic;
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
        this.updatedBy = updatedBy;
         //나중에 수정 update by
    }
}