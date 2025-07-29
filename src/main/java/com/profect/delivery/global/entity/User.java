package com.profect.delivery.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "p_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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




}
