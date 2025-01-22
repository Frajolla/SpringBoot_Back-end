package com.projeto.projeto.authentication.dto;

import com.projeto.projeto.authentication.enums.RoleName;

public record CreateUserDto(
        String email,
        String password,
        RoleName role
) {
}
