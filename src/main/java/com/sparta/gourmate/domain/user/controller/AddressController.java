package com.sparta.gourmate.domain.user.controller;

import com.sparta.gourmate.domain.user.dto.AddressRequestDto;
import com.sparta.gourmate.domain.user.dto.AddressResponseDto;
import com.sparta.gourmate.domain.user.service.AddressService;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;


    // 주소 등록
    @PostMapping("/{userId}")
    public AddressResponseDto createAddress(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid AddressRequestDto requestDto) {
        Long userId = userDetails.getUser().getId();
        return addressService.createAddress(userId, requestDto);
    }

    // 주소 수정
    @PutMapping("/{addressId}")
    public AddressResponseDto updateAddress(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long addressId,
            @RequestBody @Valid AddressRequestDto requestDto
    ) {
        Long userId = userDetails.getUser().getId();
        return addressService.updateAddress(addressId, requestDto, userId);
    }


    // 주소 조회
    @GetMapping("/{addressId}")
    public AddressResponseDto getAddress(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long addressId) {
        Long userId = userDetails.getUser().getId();
        return addressService.getAddress(addressId, userId);
    }

    // 주소 삭제 (소프트 딜리트)
    @DeleteMapping("/{addressId}")
    public void deleteAddress(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long addressId
            ) {
        Long userId = userDetails.getUser().getId();
        addressService.deleteAddress(addressId, userId);  // 삭제한 유저의 ID를 요청 파라미터로 전달
    }
}

