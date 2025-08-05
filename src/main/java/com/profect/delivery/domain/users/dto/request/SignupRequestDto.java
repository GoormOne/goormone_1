package com.profect.delivery.domain.users.dto.request;

import com.profect.delivery.global.entity.Role;

public record SignupRequestDto(
        String username,
        String password,
        String name,
        String birth,
        String email,
        Role role) {}