package com.smartinix.springauthserver.user.infrastructure.converters;

import com.smartinix.springauthserver.user.domain.User;
import com.smartinix.springauthserver.user.infrastructure.persistance.entities.Authority;
import com.smartinix.springauthserver.user.infrastructure.persistance.entities.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityConverter {

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
            .id(user.getId())
            .accountExpired(user.isAccountExpired())
            .accountLocked(user.isAccountLocked())
//            .authority(user.getAuthorities().stream().findFirst().get().getAuthority())
            .authorities(user.getAuthorities()
                             .stream()
                             .map(grantedAuthority -> Authority.fromSimpleGrantedAuthority((SimpleGrantedAuthority) grantedAuthority, UserEntityConverter.toEntity(user)))
                             .collect(Collectors.toList()))
            .credentialsExpired(user.isCredentialsExpired())
            .enabled(user.isEnabled())
            .password(user.getPassword())
            .username(user.getUsername())
            .build();
    }

    public static User toDomain(UserEntity entity) {
        return User.builder()
            .accountExpired(entity.getAccountExpired())
            .accountLocked(entity.getAccountLocked())
//            .authorities(Stream.of(new SimpleGrantedAuthority(entity.getAuthority())).collect(Collectors.toCollection(HashSet::new)))
            .authorities(entity.getAuthorities()
                             .stream()
                             .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                             .collect(Collectors.toCollection(HashSet::new)))
            .credentialsExpired(entity.getCredentialsExpired())
            .enabled(entity.getEnabled())
            .id(entity.getId())
            .password(entity.getPassword())
            .username(entity.getUsername())
            .build();
    }
}
