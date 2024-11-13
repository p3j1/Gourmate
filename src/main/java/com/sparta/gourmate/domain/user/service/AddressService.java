package com.sparta.gourmate.domain.user.service;

import com.sparta.gourmate.domain.user.dto.AddressRequestDto;
import com.sparta.gourmate.domain.user.dto.AddressResponseDto;
import com.sparta.gourmate.domain.user.entity.Address;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.repository.AddressRepository;
import com.sparta.gourmate.domain.user.repository.UserRepository;
import com.sparta.gourmate.global.exception.AddressNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    // 주소 등록
    public AddressResponseDto createAddress(Long userId, AddressRequestDto requestDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User user = userOptional.get();

        Address address = new Address(user, requestDto.getAddress(), requestDto.getAddressRequest());
        addressRepository.save(address);

        return new AddressResponseDto(address.getId(), address.getAddress(), address.getAddressRequest());
    }

    // 주소 수정
    public AddressResponseDto updateAddress(UUID addressId, AddressRequestDto requestDto) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        address.updateAddress(requestDto.getAddress(), requestDto.getAddressRequest());
        addressRepository.save(address);

        return new AddressResponseDto(address.getId(), address.getAddress(), address.getAddressRequest());
    }

    // 주소 조회 (소프트 딜리트된 주소는 제외)
    public AddressResponseDto getAddress(UUID addressId) {
        Address address = addressRepository.findById(addressId)
                .filter(a -> !a.getIsDeleted())  // 소프트 딜리트된 주소는 조회되지 않도록 처리
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        return new AddressResponseDto(address.getId(), address.getAddress(), address.getAddressRequest());
    }

    // 주소 삭제 (소프트 딜리트 처리)
    public void deleteAddress(UUID addressId, Long deletedBy) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address with ID " + addressId + " not found"));

        address.softDelete(deletedBy);  // 소프트 딜리트 처리
        addressRepository.save(address);  // 엔티티를 업데이트 (삭제 상태 반영)

    }

}

