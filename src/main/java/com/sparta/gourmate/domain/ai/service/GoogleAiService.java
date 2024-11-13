package com.sparta.gourmate.domain.ai.service;

import com.sparta.gourmate.domain.ai.dto.GoogleAiRequestDto;
import com.sparta.gourmate.domain.ai.dto.GoogleAiResponseDto;
import com.sparta.gourmate.domain.ai.entity.GoogleAi;
import com.sparta.gourmate.domain.ai.repository.GoogleAiRepository;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j(topic = "Google AI API")
@Service
public class GoogleAiService {

    private final GoogleAiRepository googleAiRepository;
    private final RestTemplate restTemplate;
    @Value("${google.ai.api.key}")
    private String apiKey;

    public GoogleAiService(GoogleAiRepository googleAiRepository, RestTemplateBuilder builder) {
        this.googleAiRepository = googleAiRepository;
        this.restTemplate = builder.build();
    }

    public GoogleAiResponseDto recommendText(User user, GoogleAiRequestDto requestDto) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://generativelanguage.googleapis.com")
                .path("/v1beta/models/gemini-1.5-flash-latest:generateContent")
                .queryParam("key", apiKey)
                .encode()
                .build()
                .toUri();
        log.info("uri = " + uri);

        String text = requestDto.getText() + "답변을 최대한 간결하게 50자 이내로 해줘.";
        log.info("text = " + text);

        String requestBody = String.format("{ \"contents\": [{\"parts\": [{\"text\": \"%s\"}]}] }", text);

        RequestEntity<String> requestEntity = RequestEntity
                .post(uri)
                .header("Content-Type", "application/json")
                .body(requestBody);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
            log.info("API Status Code: " + responseEntity.getStatusCode());

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseText = parsingTextFromResponse(responseEntity.getBody());
                GoogleAi googleAi = googleAiRepository.save(new GoogleAi(responseText, user));
                return new GoogleAiResponseDto(googleAi);
            } else {
                log.error("API Status Code: " + responseEntity.getStatusCode());
                throw new CustomException(ErrorCode.AI_EXTERNAL_API_ERROR);
            }

        } catch (HttpClientErrorException e) {
            // 클라이언트 오류 (4xx) 처리
            log.error("Client error {}: {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CustomException(ErrorCode.AI_INVALID_REQUEST);
        } catch (HttpServerErrorException e) {
            // 서버 오류 (5xx) 처리
            log.error("Server error {}: {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CustomException(ErrorCode.AI_EXTERNAL_API_ERROR);
        } catch (ResourceAccessException e) {
            // 타임아웃 오류 처리
            log.error("Timeout error: {}", e.getMessage());
            throw new CustomException(ErrorCode.AI_TIMEOUT_ERROR);
        } catch (Exception e) {
            // 그 외 모든 예외 처리
            log.error("Unexpected error: {}", e.getMessage(), e);
            throw new CustomException(ErrorCode.COMMON_SERVER_ERROR);
        }

    }

    private String parsingTextFromResponse(String responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            String text = jsonObject
                    .getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
            log.info(text);
            return text;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.AI_RESPONSE_PARSING_ERROR);
        }
    }

}
