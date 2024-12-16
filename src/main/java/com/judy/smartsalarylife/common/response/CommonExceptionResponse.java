package com.judy.smartsalarylife.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonExceptionResponse {

    private int statusCode;
    private String message;

}
