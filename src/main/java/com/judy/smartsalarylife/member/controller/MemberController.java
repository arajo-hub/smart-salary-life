package com.judy.smartsalarylife.member.controller;

import com.judy.smartsalarylife.member.request.MemberJoinRequestDto;
import com.judy.smartsalarylife.member.response.MemberJoinResponseDto;
import com.judy.smartsalarylife.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberJoinResponseDto> join(@RequestBody @Valid MemberJoinRequestDto memberJoinRequestDto) {
        return this.memberService.join(memberJoinRequestDto);
    }

}
