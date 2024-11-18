package com.sparta.gourmate.domain.user.service;

import com.sparta.gourmate.domain.user.dto.SignupRequestDto;
import com.sparta.gourmate.domain.user.dto.UserResponseDto;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.entity.UserRoleEnum;
import com.sparta.gourmate.domain.user.repository.UserRepository;
import com.sparta.gourmate.global.common.Util;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, UserRoleEnum> tokenMap = Map.of(
            "manager-token", UserRoleEnum.MANAGER,
            "master-token", UserRoleEnum.MASTER
    );

    public UserResponseDto createUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        checkUsername(username, null);

        String email = requestDto.getEmail();
        checkEmail(email, null);

        String password = encodePassword(requestDto.getPassword());

        UserRoleEnum role = getUserRole(requestDto);

        User user = new User(username, password, email, role);
        userRepository.save(user);

        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long userId) {
        return new UserResponseDto(findUser(userId));
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDto> getUsers(String query, int page, int size, String sortBy, boolean isAsc) {
        Pageable pageable = Util.createPageableWithSorting(page, size, sortBy, isAsc);

        return userRepository.findAllByUsernameContaining(query, pageable).map(UserResponseDto::new);
    }

    public UserResponseDto updateUser(Long userId, SignupRequestDto requestDto) {
        User user = findUser(userId);

        String username = requestDto.getUsername();
        checkUsername(username, userId);

        String email = requestDto.getEmail();
        checkEmail(email, userId);

        String password = encodePassword(requestDto.getPassword());

        user.update(username, password, email);

        return new UserResponseDto(user);
    }

    public void deleteUser(User user, Long userId) {
        findUser(userId).delete(user.getId());
    }

    private User findUser(Long userId) {
        return userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private void checkUsername(String username, Long userId) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent() && !checkUsername.get().getId().equals(userId)) {
            throw new CustomException(ErrorCode.USER_DUPLICATED);
        }
    }

    private void checkEmail(String email, Long userId) {
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent() && !checkEmail.get().getId().equals(userId)) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private UserRoleEnum getUserRole(SignupRequestDto requestDto) {
        UserRoleEnum role;
        if (!requestDto.getIsAdmin()) {
            role = requestDto.getIsOwner() ? UserRoleEnum.OWNER : UserRoleEnum.CUSTOMER;
        } else {
            String adminToken = requestDto.getAdminToken();
            if (!tokenMap.containsKey(adminToken)) {
                throw new CustomException(ErrorCode.INVALID_PASSWORD);
            }
            role = tokenMap.get(adminToken);
        }
        return role;
    }
}
