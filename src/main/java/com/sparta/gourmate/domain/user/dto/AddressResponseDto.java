package com.sparta.gourmate.domain.user.dto;

import com.sparta.gourmate.domain.user.entity.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddressResponseDto {

    private UUID id;
    private String address;
    private String addressRequest;

    public AddressResponseDto(Address address) {
        this.id = address.getId();
        this.address = address.getAddress();
        this.addressRequest = address.getAddressRequest();
    }
}
