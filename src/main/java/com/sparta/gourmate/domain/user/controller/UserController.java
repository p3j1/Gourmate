package com.sparta.gourmate.domain.user.controller;

import com.sparta.gourmate.domain.user.dto.SignupRequestDto;
import com.sparta.gourmate.domain.user.dto.UserResponseDto;
import com.sparta.gourmate.domain.user.service.UserService;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto createUser(@RequestBody @Valid SignupRequestDto requestDto) {
        return userService.createUser(requestDto);
    }

    @PutMapping
    public UserResponseDto updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid SignupRequestDto requestDto) {
        return userService.updateUser(userDetails.getUser().getId(), requestDto);
    }

}
