package com.judy.smartsalarylife.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_SALARY_USAGE_INFO_SUM(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "월급 사용 정보 금액의 합은 월급과 일치해야 합니다."),
    FUTURE_SALARY_DATE(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "현재까지의 월급만 입력 가능합니다."),
    INVALID_LENGTH_SALARY_USAGE_INFO(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "입력 가능한 월급 사용 정보 길이를 초과했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
