package com.smartinix.springauthserver.user.api;

import com.smartinix.springauthserver.user.api.view.UserView;
import com.smartinix.springauthserver.user.application.UserService;
import com.smartinix.springauthserver.user.application.dto.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public List<UserView> getUsers() {
        return service.getUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserView saveUser(@Valid @NotNull @RequestBody UserDto userDto) {
        return service.saveUser(userDto);
    }

    @GetMapping("/{username}")
    public UserView getUserByUsername(@PathVariable String username) {
        return service.findUserByUsername(username);
    }
}
