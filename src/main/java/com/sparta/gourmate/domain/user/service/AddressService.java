package com.sparta.gourmate.domain.user.service;

import com.sparta.gourmate.domain.user.dto.AddressRequestDto;
import com.sparta.gourmate.domain.user.dto.AddressResponseDto;
import com.sparta.gourmate.domain.user.entity.Address;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.repository.AddressRepository;
import com.sparta.gourmate.domain.user.repository.UserRepository;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    // 주소 등록
    public AddressResponseDto createAddress(Long userId, AddressRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_EXISTS));
        Address address = new Address(user, requestDto.getAddress(), requestDto.getAddressRequest());
        addressRepository.save(address);

        return new AddressResponseDto(address.getId(), address.getAddress(), address.getAddressRequest());
    }

    // 주소 수정
    public AddressResponseDto updateAddress(Long addressId, AddressRequestDto requestDto, Long userId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new CustomException(ErrorCode.ADDRESS_NOT_FOUND));

        if (!Objects.equals(address.getUser().getId(),userId)){
            throw new CustomException(ErrorCode.USER_NOT_SAME);
        }

        address.updateAddress(requestDto.getAddress(), requestDto.getAddressRequest());

        return new AddressResponseDto(address.getId(), address.getAddress(), address.getAddressRequest());
    }

    // 주소 조회 (소프트 딜리트된 주소는 제외)
    @Transactional(readOnly = true)
    public AddressResponseDto getAddress(Long addressId, Long userId) {
        Address address = addressRepository.findById(addressId)
                .filter(a -> !a.getIsDeleted())  // 소프트 딜리트된 주소는 조회되지 않도록 처리
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTS));

        return new AddressResponseDto(address.getId(), address.getAddress(), address.getAddressRequest());
    }

    // 주소 삭제 (소프트 딜리트 처리)
    public void deleteAddress(Long addressId, Long deletedBy) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new CustomException(ErrorCode.ADDRESS_NOT_FOUND,"id:"+addressId));

        address.softDelete(deletedBy);  // 소프트 딜리트 처리

    }

}

