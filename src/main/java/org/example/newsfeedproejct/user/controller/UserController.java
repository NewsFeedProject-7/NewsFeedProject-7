package org.example.newsfeedproejct.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.global.consts.Const;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.CommonErrorCode;
import org.example.newsfeedproejct.user.dto.UserLoginDto;
import org.example.newsfeedproejct.user.dto.UserSearchDetailDto;
import org.example.newsfeedproejct.user.dto.UserSignUpDto;
import org.example.newsfeedproejct.user.dto.UserUpdateDto;
import org.example.newsfeedproejct.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(
            @Valid @RequestBody UserLoginDto.Request requestDto,
            HttpServletRequest httpRequest
    ) {

        UserLoginDto.Response userLoginResponseDto = userService.login(
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        HttpSession session = httpRequest.getSession();
        session.setAttribute(Const.LOGIN_USER, userLoginResponseDto.getId());
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserSearchDetailDto.Response findById(
            @PathVariable Long userId,
            @SessionAttribute(Const.LOGIN_USER) Long loginUserId) {
        return userService.findById(userId, loginUserId);
    }

    @PatchMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserUpdateDto.Response updateUser(
            @PathVariable Long userId,
            @SessionAttribute(Const.LOGIN_USER) Long loginUserId,
            @Valid @RequestBody UserUpdateDto.Request requestDto
    ) {

        return userService.updateUser(
                userId,
                loginUserId,
                requestDto.getNickname(),
                requestDto.getCurrentPassword(),
                requestDto.getChangePassword(),
                requestDto.getConfirmPassword()
        );
    }

}
