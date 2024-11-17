package com.sparta.gourmate.domain.user.controller;

import com.sparta.gourmate.domain.user.dto.AddressRequestDto;
import com.sparta.gourmate.domain.user.dto.AddressResponseDto;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.service.AddressService;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users/addresses")
public class AddressController {

    private final AddressService addressService;

    // 주소 등록
    @PostMapping
    public AddressResponseDto createAddress(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody AddressRequestDto requestDto) {
        User user = userDetails.getUser();
        return addressService.createAddress(user, requestDto);
    }

    // 주소 수정
    @PutMapping("/{addressId}")
    public AddressResponseDto updateAddress(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID addressId,
            @Valid @RequestBody AddressRequestDto requestDto) {
        User user = userDetails.getUser();
        return addressService.updateAddress(addressId, requestDto, user);
    }

    // 주소 조회
    @GetMapping("/{addressId}")
    public AddressResponseDto getAddress(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID addressId) {
        User user = userDetails.getUser();
        return addressService.getAddress(addressId, user);
    }

    //목록 조회 페이징
    @GetMapping
    public Page<AddressResponseDto> getAddressList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean isAsc) {
        User user = userDetails.getUser();
        return addressService.getAddressList(user, page, size, sortBy, isAsc);
    }

    // 주소 삭제 (소프트 딜리트)
    @DeleteMapping("/{addressId}")
    public void deleteAddress(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID addressId) {
        User user = userDetails.getUser();
        addressService.deleteAddress(addressId, user);
    }
}
