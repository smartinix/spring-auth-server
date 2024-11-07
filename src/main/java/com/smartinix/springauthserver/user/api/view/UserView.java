package com.smartinix.springauthserver.user.api.view;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class UserView {
    private UUID id;

    private String password;

    private  String username;

    private Set<GrantedAuthority> authorities;

    private  boolean accountExpired;

    private  boolean accountLocked;

    private  boolean credentialsExpired;

    private  boolean enabled;
}
