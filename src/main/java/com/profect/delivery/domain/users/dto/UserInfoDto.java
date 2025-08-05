package com.profect.delivery.domain.users.dto;

import com.profect.delivery.global.entity.Role;

public record UserInfoDto(String username, Role role, String name, String email) {}