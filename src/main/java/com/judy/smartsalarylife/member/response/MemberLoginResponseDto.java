package com.judy.smartsalarylife.member.response;

import lombok.Getter;

@Getter
public class MemberLoginResponseDto {

    private String accessToken;
    private String refreshToken;

}
