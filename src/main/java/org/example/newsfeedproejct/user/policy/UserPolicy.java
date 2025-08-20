package org.example.newsfeedproejct.user.policy;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.global.config.PasswordEncoder;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.UserErrorCode;
import org.example.newsfeedproejct.user.entity.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPolicy {

    private final PasswordEncoder passwordEncoder;

    public void checkOwnerOrThrow(User user, Long userId) {
        if (false == user.isOwnedBy(userId)) {
            throw new GlobalException(UserErrorCode.ACCESS_DENIED);
        }
    }

    public void checkCurrentPasswordOrThrow(User user, String currentPassword) {
        if (false == passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new GlobalException(UserErrorCode.CURRENT_PASSWORD_NOT_MATCHED);
        }
    }
}
