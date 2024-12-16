package com.judy.smartsalarylife.member.service;

import com.judy.smartsalarylife.common.exception.SmartSalaryLifeException;
import com.judy.smartsalarylife.common.exception.enums.ErrorCode;
import com.judy.smartsalarylife.member.domain.Member;
import com.judy.smartsalarylife.member.repository.MemberRepository;
import com.judy.smartsalarylife.member.request.MemberJoinRequestDto;
import com.judy.smartsalarylife.member.request.MemberLoginRequestDto;
import com.judy.smartsalarylife.member.response.MemberJoinResponseDto;
import com.judy.smartsalarylife.member.response.MemberLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    public ResponseEntity<MemberJoinResponseDto> join(MemberJoinRequestDto memberJoinRequestDto) {
        Member member = modelMapper.map(memberJoinRequestDto, Member.class);
        member.setEncryptedPassword(encoder.encode(memberJoinRequestDto.getPassword()));
        this.memberRepository.save(member);
        MemberJoinResponseDto response = modelMapper.map(member, MemberJoinResponseDto.class);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<MemberLoginResponseDto> login(MemberLoginRequestDto memberLoginRequestDto) {
        // DB에 존재하는 사용자인지 확인
        Member member = this.memberRepository.findByEmail(memberLoginRequestDto.getEmail()).orElseThrow(() -> new SmartSalaryLifeException(ErrorCode.MEMBER_NOT_EXIST));
        // 비밀번호 확인

        if (!encoder.matches(memberLoginRequestDto.getPassword(), member.getEncryptedPassword())) {
            throw new SmartSalaryLifeException(ErrorCode.PASSWORD_MISMATCH);
        }

        MemberLoginResponseDto response = modelMapper.map(member, MemberLoginResponseDto.class);
        return ResponseEntity.ok(response);
    }
}
