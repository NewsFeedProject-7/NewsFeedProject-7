package org.example.newsfeedproejct.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.user.dto.UserSignUpDto;
import org.example.newsfeedproejct.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(
            @Valid @RequestBody UserSignUpDto.Request requestDto
    ) {

        userService.signup(
                requestDto.getNickname(),
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getConfirmPassword()
        );
    }

}
