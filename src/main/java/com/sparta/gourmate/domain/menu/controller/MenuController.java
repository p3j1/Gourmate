package com.sparta.gourmate.domain.menu.controller;

import com.sparta.gourmate.domain.menu.dto.MenuRequestDto;
import com.sparta.gourmate.domain.menu.dto.MenuResponseDto;
import com.sparta.gourmate.domain.menu.service.MenuService;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/menu")
    public ResponseEntity<MenuResponseDto> createMenu(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @Valid @RequestBody MenuRequestDto requestDto) {
        User user = userDetails.getUser();
        MenuResponseDto responseDto = menuService.createMenu(user, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    }

}
