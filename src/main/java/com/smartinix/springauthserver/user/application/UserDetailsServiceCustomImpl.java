package com.smartinix.springauthserver.user.application;

import com.smartinix.springauthserver.user.domain.User;
import com.smartinix.springauthserver.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceCustomImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                                                                      user.getPassword(),
                                                                      user.isEnabled(),
                                                                      !user.isAccountExpired(),
                                                                      !user.isCredentialsExpired(),
                                                                      !user.isAccountLocked(),
                                                                      user.getAuthorities().stream().toList());
    }
}
