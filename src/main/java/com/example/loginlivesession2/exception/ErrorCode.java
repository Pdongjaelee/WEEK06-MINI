package com.example.loginlivesession2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // JWT 관련
    JWT_BAD_TOKEN_401(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    JWT_EXPIRED_TOKEN_401(HttpStatus.UNAUTHORIZED, "다시 로그인 해주세요."),

    // 회원가입 관련
    USERID_DUPLICATION_409(HttpStatus.CONFLICT, "이미 가입된 회원입니다."),



    // 로그인 관련
    USER_NOT_FOUND_404(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // NOT FOUND
    FOLDER_ID_NOT_FOUND_404(HttpStatus.NOT_FOUND, "폴더 id를 찾을 수 없습니다."),
    PHOTO_ID_NOT_FOUND_404(HttpStatus.NOT_FOUND, "포토 id를 찾을 수 없습니다."),


    UNAUTHORIZED_401(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    /*
     * 해당 주석 위로 enum 코드 추가 바랍니다.
     * 코드 추가시 간편하게 진행하기 위해 생성한 미사용 코드입니다. 사용하지마세요.
     * 생성이유 : enum 마지막 요소에 ; 을 입력해야하기에, 끝부분에 추가하게 될 경우 ; 을 재입력해야함
     */
    DO_NOT_USED(null, null);


    private final HttpStatus httpStatus;
    private final String message;
}
