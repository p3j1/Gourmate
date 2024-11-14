package com.sparta.gourmate.domain.user.controller;

import com.sparta.gourmate.domain.user.dto.AddressRequestDto;
import com.sparta.gourmate.domain.user.dto.AddressResponseDto;
import com.sparta.gourmate.domain.user.entity.Address;
import com.sparta.gourmate.domain.user.repository.AddressRepository;
import com.sparta.gourmate.domain.user.service.AddressService;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users/addresses")
public class AddressController {

    private final AddressService addressService;
    private final AddressRepository addressRepository;


    // 주소 등록
    @PostMapping
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

    //목록 조회 페이징
    @GetMapping
    public List<AddressResponseDto> getAddressList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam boolean inAsc
    ) {
        Long userId = userDetails.getUser().getId();
        return addressService.getAddressList(userId, page, size, sortBy, inAsc);
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

