package com.smartinix.springauthserver.user.application.converters;

import com.smartinix.springauthserver.user.api.view.UserView;
import com.smartinix.springauthserver.user.application.dto.UserDto;
import com.smartinix.springauthserver.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {

    public static User fromDtoToDomain(UserDto userDto) {
        return User.builder()
//            .authorities(Stream.of(new SimpleGrantedAuthority(userDto.getAuthority())).collect(Collectors.toCollection(HashSet::new)))
            .authorities(userDto.getAuthorities())
            .username(userDto.getUsername())
            .password(userDto.getPassword())
            .id(userDto.getId())
            .enabled(userDto.isEnabled())
            .credentialsExpired(userDto.isCredentialsExpired())
            .accountLocked(userDto.isAccountLocked())
            .accountExpired(userDto.isAccountExpired())
            .build();
    }

    public static UserView toView(User user) {
        return UserView.builder()
            .accountExpired(user.isAccountExpired())
            .accountLocked(user.isAccountLocked())
            .username(user.getUsername())
            .password(user.getPassword())
            .enabled(user.isEnabled())
            .credentialsExpired(user.isCredentialsExpired())
//            .authority(user.getAuthorities().stream().findFirst().get().getAuthority())
            .authorities(user.getAuthorities())
            .id(user.getId())
            .build();
    }
}
