package com.sparta.gourmate.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDto {

    @NotBlank(message = "ADDRESS_EMPTY")
    private String address; // 주소

    private String addressRequest; // 추가 요청 사항 (선택 사항)
}
