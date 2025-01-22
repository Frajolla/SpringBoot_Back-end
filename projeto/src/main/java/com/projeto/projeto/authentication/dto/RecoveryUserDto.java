package com.projeto.projeto.authentication.dto;

import com.projeto.projeto.authentication.entitities.Role;

import java.util.List;

public record RecoveryUserDto(

        Long id,

        String email,

        List<Role> roles
) {
}
