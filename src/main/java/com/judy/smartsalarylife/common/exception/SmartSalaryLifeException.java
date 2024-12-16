package com.judy.smartsalarylife.common.exception;

import com.judy.smartsalarylife.common.exception.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SmartSalaryLifeException extends RuntimeException {

    private ErrorCode errorCode;
}
