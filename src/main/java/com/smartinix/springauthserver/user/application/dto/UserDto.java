package com.smartinix.springauthserver.user.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;

import java.util.Set;
import java.util.UUID;

@Getter
@Validated
@Builder
@ToString
public class UserDto {
    private final UUID id;

    @NotNull
    private final String password;

    @NotNull
    private final String username;

    private final Set<GrantedAuthority> authorities;

    private final boolean accountExpired;

    private final boolean accountLocked;

    private final boolean credentialsExpired;

    private final boolean enabled;
}
