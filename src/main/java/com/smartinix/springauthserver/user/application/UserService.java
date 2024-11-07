package com.smartinix.springauthserver.user.application;

import com.smartinix.springauthserver.user.api.view.UserView;
import com.smartinix.springauthserver.user.application.converters.UserConverter;
import com.smartinix.springauthserver.user.application.dto.UserDto;
import com.smartinix.springauthserver.user.domain.User;
import com.smartinix.springauthserver.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserView saveUser(UserDto userDto) {
        log.info("Saving user");
        User userToSave = UserConverter.fromDtoToDomain(userDto);
        User savedUser = userRepository.save(userToSave);
        log.info("Saved user id: " + savedUser.getId());
        return UserConverter.toView(savedUser);
    }

    public UserView findUserByUsername(String username) {
        log.info("Searching for user with username " + username);
        User user = userRepository.findUserByUsername(username).orElse(null);
        UserView userView = null;
        if (user != null) {
            userView = UserConverter.toView(user);
            log.info("User with username " + username + " has been found!");
        }
        return userView;
    }

    public List<UserView> getUsers() {
        log.info("Retrieving the list of users");
        return userRepository.getUsers()
            .stream().map(UserConverter::toView)
            .collect(Collectors.toList());
    }
}
