package com.sparta.gourmate.domain.user.controller;

import com.sparta.gourmate.domain.user.dto.AddressRequestDto;
import com.sparta.gourmate.domain.user.dto.AddressResponseDto;
import com.sparta.gourmate.domain.user.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    // 주소 등록
    @PostMapping("/{userId}")
    public AddressResponseDto createAddress(@PathVariable Long userId, @RequestBody @Valid AddressRequestDto requestDto) {
        return addressService.createAddress(userId, requestDto);
    }

    // 주소 수정
    @PutMapping("/{addressId}")
    public AddressResponseDto updateAddress(@PathVariable UUID addressId, @RequestBody @Valid AddressRequestDto requestDto) {
        return addressService.updateAddress(addressId, requestDto);
    }

    // 주소 조회
    @GetMapping("/{addressId}")
    public AddressResponseDto getAddress(@PathVariable UUID addressId) {
        return addressService.getAddress(addressId);
    }

    // 주소 삭제 (소프트 딜리트)
    @DeleteMapping("/{addressId}")
    public void deleteAddress(@PathVariable UUID addressId, @RequestParam Long deletedBy) {
        addressService.deleteAddress(addressId, deletedBy);  // 삭제한 유저의 ID를 요청 파라미터로 전달
    }
}

