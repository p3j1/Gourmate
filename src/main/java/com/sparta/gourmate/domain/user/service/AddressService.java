package com.sparta.gourmate.domain.user.service;

import com.sparta.gourmate.domain.user.dto.AddressRequestDto;
import com.sparta.gourmate.domain.user.dto.AddressResponseDto;
import com.sparta.gourmate.domain.user.entity.Address;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.repository.AddressRepository;
import com.sparta.gourmate.global.common.Util;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;

    // 주소 등록
    public AddressResponseDto createAddress(User user, AddressRequestDto requestDto) {
        Address address = new Address(user, requestDto.getAddress(), requestDto.getAddressRequest());
        addressRepository.save(address);

        return new AddressResponseDto(address);
    }

    // 주소 수정
    public AddressResponseDto updateAddress(UUID addressId, AddressRequestDto requestDto, User user) {
        Address address = findAddress(addressId);
        checkAddressUser(user, address);

        address.update(requestDto);

        return new AddressResponseDto(address);
    }

    // 주소 상세 조회 (소프트 딜리트된 주소는 제외)
    @Transactional(readOnly = true)
    public AddressResponseDto getAddress(UUID addressId, User user) {
        Address address = findAddress(addressId);
        checkAddressUser(user, address);

        return new AddressResponseDto(address);
    }

    //주소 목록 getAddressList
    @Transactional(readOnly = true)
    public Page<AddressResponseDto> getAddressList(User user, int page, int size, String sortBy, boolean inAsc) {
        Pageable pageable = Util.createPageableWithSorting(page, size, sortBy, inAsc);
        Page<Address> addressList = addressRepository.findAllByUserIdAndIsDeletedFalse(user.getId(), pageable);

        return addressList.map(AddressResponseDto::new);
    }

    // 주소 삭제 (소프트 딜리트 처리)
    public void deleteAddress(UUID addressId, User user) {
        Address address = findAddress(addressId);
        checkAddressUser(user, address);
        address.delete(user.getId());  // 소프트 딜리트 처리
    }

    private Address findAddress(UUID addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new CustomException(ErrorCode.ADDRESS_NOT_FOUND));
    }

    private void checkAddressUser(User user, Address address) {
        if (!Objects.equals(address.getUser().getId(), user.getId())) {
            throw new CustomException(ErrorCode.USER_NOT_SAME);
        }
    }
}
