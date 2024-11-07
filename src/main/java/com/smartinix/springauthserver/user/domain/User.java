package com.smartinix.springauthserver.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;

    private String password;

    private  String username;

    private  Set<GrantedAuthority> authorities;

    private  boolean accountExpired;

    private  boolean accountLocked;

    private  boolean credentialsExpired;

    private  boolean enabled;
}
