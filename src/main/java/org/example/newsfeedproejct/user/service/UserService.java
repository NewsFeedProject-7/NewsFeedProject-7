package org.example.newsfeedproejct.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.follow.repository.FollowRepository;
import org.example.newsfeedproejct.global.config.PasswordEncoder;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.UserErrorCode;
import org.example.newsfeedproejct.user.dto.UserLoginDto;
import org.example.newsfeedproejct.user.dto.UserUpdateDto;
import org.example.newsfeedproejct.user.dto.UserSearchDetailDto;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.policy.UserPolicy;
import org.example.newsfeedproejct.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final UserPolicy userPolicy;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(String nickname, String email, String password, String confirmPassword) {

        if (false == ObjectUtils.nullSafeEquals(password, confirmPassword)) {
            throw new GlobalException(UserErrorCode.CONFIRM_PASSWORD_NOT_MATCHED);
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

    @Transactional(readOnly = true)
    public UserSearchDetailDto.Response findById(Long userId, Long loginUserId) {
        User foundUser = userRepository.findByIdOrElseThrow(userId);
        if (userId.equals(loginUserId)) {
            return UserSearchDetailDto.Response.from(foundUser);
        } else {

            boolean isFollowing = followRepository.existsByFollowerIdAndFollowingId(loginUserId, userId);

            return UserSearchDetailDto.Response.builder()
                    .nickname(foundUser.getNickname())
                    .email(null)
                    .isFollowing(isFollowing)
                    .build();
        }
    }

    @Transactional
    public UserUpdateDto.Response updateUser(
            Long userId,
            Long loginUserId,
            String newNickname,
            String currentPassword,
            String newPassword,
            String confirmPassword
    ) {

        User findUser = userRepository.findByIdOrElseThrow(userId);

        // 본인의 정보만 수정 가능
        userPolicy.checkOwnerOrThrow(findUser, loginUserId);

        // 본인확인용 비밀번호 확인
        userPolicy.checkCurrentPasswordOrThrow(findUser, currentPassword);

        // 비밀번호 값이 있다면 업데이트
        applyPasswordChange(findUser, newPassword, confirmPassword);

        // 닉네임 값이 있다면 업데이트
        applyNicknameChange(findUser, newNickname);

        userRepository.flush();

        return UserUpdateDto.Response.from(findUser);
    }

    @Transactional
    public void deleteUser(
            Long userId,
            Long loginUserId,
            String currentPassword
    ) {

        User findUser = userRepository.findByIdOrElseThrow(userId);

        // 본인의 계정만 삭제 가능
        userPolicy.checkOwnerOrThrow(findUser, loginUserId);

        // 본인확인용 비밀번호 확인
        userPolicy.checkCurrentPasswordOrThrow(findUser, currentPassword);

        findUser.softDelete();
    }

    private void applyPasswordChange(User user, String newPassword, String confirmPassword) {
        boolean hasPassword = StringUtils.hasText(newPassword);
        boolean hasConfirmPassword = StringUtils.hasText(confirmPassword);

        if (hasPassword || hasConfirmPassword) {
            if (false == (hasPassword && hasConfirmPassword)) {
                throw new GlobalException(UserErrorCode.INVALID_PASSWORD_INPUT);
            }

            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw new GlobalException(UserErrorCode.SAME_AS_OLD_PASSWORD);
            }

            if (false == ObjectUtils.nullSafeEquals(newPassword, confirmPassword)) {
                throw new GlobalException(UserErrorCode.CONFIRM_PASSWORD_NOT_MATCHED);
            }

            String encodedPassword = passwordEncoder.encode(newPassword);
            user.updatePassword(encodedPassword);
        }
    }

    private void applyNicknameChange(User user, String newNickname) {
        boolean hasNickname = StringUtils.hasText(newNickname);

        if (hasNickname) {
            if (userRepository.existsByNicknameAndIdNot(newNickname, user.getId())) {
                throw new GlobalException(UserErrorCode.DUPLICATE_NICKNAME);
            }

            if (false == ObjectUtils.nullSafeEquals(newNickname, user.getNickname())) {
                user.updateNickname(newNickname);
            }
        }
    }
}
