package org.example.newsfeedproejct.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeedproejct.global.config.PasswordEncoder;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.UserErrorCode;
import org.example.newsfeedproejct.user.dto.UserLoginDto;
import org.example.newsfeedproejct.user.dto.UserUpdateDto;
import org.example.newsfeedproejct.user.dto.UserSearchDetailDto;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
            return UserSearchDetailDto.Response.builder()
                    .nickname(foundUser.getNickname())
                    .email(null)
                    .build();
        }
    }

    @Transactional
    public UserUpdateDto.Response updateUser(
            Long userId,
            Long loginUserId,
            String nickname,
            String currentPassword,
            String changePassword,
            String confirmPassword
    ) {

        User findUser = userRepository.findByIdOrElseThrow(userId);

        // 본인의 정보만 수정 가능
        if (false == ObjectUtils.nullSafeEquals(findUser.getId(), loginUserId)) {
            throw new GlobalException(UserErrorCode.UNAUTHORIZED_UPDATE);
        }

        // 본인확인용 비밀번호 확인
        if (false == passwordEncoder.matches(currentPassword, findUser.getPassword())) {
            throw new GlobalException(UserErrorCode.CURRENT_PASSWORD_NOT_MATCHED);
        }

        // 비밀번호 값이 있다면 업데이트
        boolean hasPassword = StringUtils.hasText(changePassword);
        boolean hasConfirmPassword = StringUtils.hasText(confirmPassword);

        if (hasPassword || hasConfirmPassword) {
            if (false == (hasPassword && hasConfirmPassword)) {
                throw new GlobalException(UserErrorCode.INVALID_PASSWORD_INPUT);
            }

            if (passwordEncoder.matches(changePassword, findUser.getPassword())) {
                throw new GlobalException(UserErrorCode.SAME_AS_OLD_PASSWORD);
            }

            if (false == ObjectUtils.nullSafeEquals(changePassword, confirmPassword)) {
                throw new GlobalException(UserErrorCode.CONFIRM_PASSWORD_NOT_MATCHED);
            }

            String encodedPassword = passwordEncoder.encode(changePassword);
            findUser.updatePassword(encodedPassword);
        }

        // 닉네임 값이 있다면 업데이트
        boolean hasNickname = StringUtils.hasText(nickname);

        if (hasNickname) {
            if (userRepository.existsByNicknameAndIdNot(nickname, findUser.getId())) {
                throw new GlobalException(UserErrorCode.DUPLICATE_NICKNAME);
            }

            if (false == ObjectUtils.nullSafeEquals(nickname, findUser.getNickname())) {
                findUser.updateNickname(nickname);
            }
        }

        userRepository.flush();

        return UserUpdateDto.Response.from(findUser);
    }
}
