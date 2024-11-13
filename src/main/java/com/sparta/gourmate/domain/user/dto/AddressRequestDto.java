package com.sparta.gourmate.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDto {

    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    private String address; // 주소

    private String addressRequest; // 추가 요청 사항 (선택 사항)
}
