package com.judy.smartsalarylife.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginResponseDto {

    private String email;
    private String accessToken;
    private String refreshToken;

}
