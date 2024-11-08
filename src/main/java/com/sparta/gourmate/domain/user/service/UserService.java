package com.sparta.gourmate.domain.user.service;

import com.sparta.gourmate.domain.user.dto.SignupRequestDto;
import com.sparta.gourmate.domain.user.dto.UserResponseDto;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.entity.UserRoleEnum;
import com.sparta.gourmate.domain.user.repository.UserRepository;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

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
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 이름입니다.");
        }

        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }

        UserRoleEnum role;
        if (!requestDto.getIsAdmin()) {
            role = requestDto.getIsOwner() ? UserRoleEnum.OWNER : UserRoleEnum.CUSTOMER;
        } else {
            String adminToken = requestDto.getAdminToken();
            if (!tokenMap.containsKey(adminToken)) {
                throw new IllegalArgumentException("관리자 암호가 틀렸습니다.");
            }
            role = tokenMap.get(adminToken);
        }

        User user = new User(username, password, email, role);
        userRepository.save(user);

        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, SignupRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTH_MEMBER_NOT_EXISTS));

        user.updateInfo(
                requestDto.getUsername(),
                passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getEmail()
        );

        return new UserResponseDto(user);
    }
}
