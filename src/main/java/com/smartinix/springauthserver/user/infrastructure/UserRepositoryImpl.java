package com.smartinix.springauthserver.user.infrastructure;

import com.smartinix.springauthserver.user.domain.User;
import com.smartinix.springauthserver.user.domain.repository.UserRepository;
import com.smartinix.springauthserver.user.infrastructure.converters.UserEntityConverter;
import com.smartinix.springauthserver.user.infrastructure.persistance.JpaUserRepository;
import com.smartinix.springauthserver.user.infrastructure.persistance.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;


    @Override
    public User save(User user) {
        log.info("Saving user");
        UserEntity userEntityToSave = UserEntityConverter.toEntity(user);
        UserEntity savedUserEntity = jpaUserRepository.save(userEntityToSave);
        log.info("Saved user with id " + savedUserEntity.getId());
        return UserEntityConverter.toDomain(savedUserEntity);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        log.info("Find user by username " + username);
        return jpaUserRepository.findByUsername(username).map(UserEntityConverter::toDomain);
    }

    @Override
    public List<User> getUsers() {
        log.info("JPA repo, getUsers()");
        return jpaUserRepository.findAll()
            .stream().map(UserEntityConverter::toDomain)
            .collect(Collectors.toList());
    }
}
