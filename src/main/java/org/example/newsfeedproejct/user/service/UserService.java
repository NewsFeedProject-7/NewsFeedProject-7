package org.example.newsfeedproejct.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.global.config.PasswordEncoder;
import org.example.newsfeedproejct.global.exception.CommonErrorCode;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void signup(String nickname, String email, String password, String confirmPassword) {

        if (false == ObjectUtils.nullSafeEquals(password, confirmPassword)) {
            throw new GlobalException(CommonErrorCode.PASSWORD_NOT_MATCHED);
        }

        if (userRepository.existsByEmail(email)) {
            throw new GlobalException(CommonErrorCode.DUPLICATE_RESOURCE);
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new GlobalException(CommonErrorCode.DUPLICATE_RESOURCE);
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(nickname, email, encodedPassword);
        userRepository.save(user);
    }
}
