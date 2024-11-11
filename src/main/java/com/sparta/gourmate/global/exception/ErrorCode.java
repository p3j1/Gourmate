package com.sparta.gourmate.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    COMMON_INVALID_PARAMETER("잘못된 파라미터입니다.", BAD_REQUEST),
    COMMON_SERVER_ERROR("서버에서 에러가 발생하였습니다.", INTERNAL_SERVER_ERROR),
    AUTH_AUTHENTICATION_FAILED("인증에 실패하셨습니다.", UNAUTHORIZED),
    AUTH_AUTHORIZATION_FAILED("권한이 없습니다.", FORBIDDEN),
    AUTH_ACCOUNT_NAME_BLANK("계정 이름은 공백일 수 없습니다.", BAD_REQUEST),
    AUTH_JWT_CLAIMS_EMPTY("JWT claims 문자열이 비어 있습니다.", UNAUTHORIZED),
    AUTH_JWT_EXPIRED("만료된 토큰입니다.", UNAUTHORIZED),
    AUTH_JWT_INVALID("잘못된 토큰입니다.", UNAUTHORIZED),
    AUTH_JWT_UNPRIVILEGED("권한이 없는 토큰입니다.", FORBIDDEN),
    AUTH_JWT_UNSUPPORTED("지원되지 않는 토큰입니다.", UNAUTHORIZED),
    AUTH_MEMBER_NOT_EXISTS("존재하지 않는 사용자입니다.", BAD_REQUEST),
    AUTH_PASSWORD_BLANK("비밀번호는 공백일 수 없습니다.", BAD_REQUEST),

    USER_NOT_SAME("해당 작성자가 아닙니다.", BAD_REQUEST),

    STORE_NOT_EXISTS("가게Id가 존재하지 않습니다.", BAD_REQUEST),
    STORE_NOT_FOUND("가게가 존재하지 않습니다.", NOT_FOUND),

    MENU_NOT_FOUND("메뉴가 존재하지 않습니다.", NOT_FOUND),
    MENU_NAME_EMPTY("메뉴 이름이 존재하지 않습니다.", BAD_REQUEST),
    MENU_PRICE_EMPTY("메뉴 가격이 존재하지 않습니다.", BAD_REQUEST),
    MENU_PRICE_INVALID("메뉴 가격이 유효하지 않습니다.", BAD_REQUEST),
    MENU_STATUS_EMPTY("메뉴 상태가 존재하지 않습니다.",BAD_REQUEST),
    MENU_STATUS_INVALID("메뉴 상태 값이 유효하지 않습니다.",BAD_REQUEST),

    ;

    private final String message;
    private final HttpStatus httpStatus;

}
