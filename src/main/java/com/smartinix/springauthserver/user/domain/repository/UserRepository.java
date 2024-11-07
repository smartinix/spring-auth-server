package com.smartinix.springauthserver.user.domain.repository;

import com.smartinix.springauthserver.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findUserByUsername(String username);
    List<User> getUsers();
}
