package com.profect.delivery.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {//컬럼 어노테이션 적용하기
    @Id
    private String user_id;

    private String username;
    private String password;
    private String name;
    private Date birth;
    private String email;
    private Role role;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean is_pubilc=true;
    private Boolean is_banned=false;
    private Timestamp created_at;
    private String created_by;
    private Timestamp updated_at;
    private String updated_by;
    private Timestamp deleted_at;
    private String deleted_by;
    private String deleted_rs;




}
