package com.judy.smartsalarylife.advice;

import com.judy.smartsalarylife.common.exception.SmartSalaryLifeException;
import com.judy.smartsalarylife.common.response.CommonExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(SmartSalaryLifeException.class)
    public ResponseEntity<CommonExceptionResponse> handleSmartSalaryLifeException(SmartSalaryLifeException ex) {
        CommonExceptionResponse response = CommonExceptionResponse.builder().statusCode(ex.getErrorCode().getCode()).message(ex.getErrorCode().getMessage()).build();
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(response);
    }

}
