package org.example.newsfeedproejct.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.global.config.PasswordEncoder;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.UserErrorCode;
import org.example.newsfeedproejct.user.dto.UserLoginDto;
import org.example.newsfeedproejct.user.dto.UserSearchDetailDto;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(String nickname, String email, String password, String confirmPassword) {

        if (false == ObjectUtils.nullSafeEquals(password, confirmPassword)) {
            throw new GlobalException(UserErrorCode.PASSWORD_NOT_MATCHED);
        }

        if (userRepository.existsByEmail(email)) {
            throw new GlobalException(UserErrorCode.DUPLICATE_EMAIL);
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new GlobalException(UserErrorCode.DUPLICATE_NICKNAME);
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(nickname, email, encodedPassword);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserLoginDto.Response login(String email, String password) {

        User findUser = userRepository.findByEmailOrElseThrow(email);

        findUser.verifyPasswordOrThrow(passwordEncoder, password);

        return UserLoginDto.Response.builder()
                .id(findUser.getId())
                .build();
    }

    @Transactional
    public UserSearchDetailDto.Response findById(Long userId) {
        User findById = userRepository.findById(userId).orElseThrow(
                () -> new GlobalException(UserErrorCode.USER_NOT_FOUND)
        );
        return new UserSearchDetailDto.Response(findById);
    }
}
