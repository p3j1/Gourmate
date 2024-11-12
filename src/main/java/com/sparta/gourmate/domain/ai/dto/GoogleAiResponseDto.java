package com.sparta.gourmate.domain.ai.dto;

import com.sparta.gourmate.domain.ai.entity.GoogleAi;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GoogleAiResponseDto {
    private final UUID id;
    private final String text;

    public GoogleAiResponseDto(GoogleAi googleAi) {
        this.id = googleAi.getId();
        this.text = googleAi.getResponse();
    }

}
