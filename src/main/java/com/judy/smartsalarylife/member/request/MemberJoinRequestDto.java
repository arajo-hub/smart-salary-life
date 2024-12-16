package com.judy.smartsalarylife.member.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class MemberJoinRequestDto {

    @Email
    private String email;
    @Length(min = 1, max = 15)
    private String nickname;
    @NotBlank
    private String password;

}
