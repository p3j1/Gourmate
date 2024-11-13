package com.sparta.gourmate.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddressResponseDto {

    private UUID id;
    private String address;
    private String addressRequest;

    // 생성자
    public AddressResponseDto(UUID id, String address, String addressRequest) {
        this.id = id;
        this.address = address;
        this.addressRequest = addressRequest;
    }
}
