package com.judy.smartsalarylife.member.service;

import com.judy.smartsalarylife.member.domain.Member;
import com.judy.smartsalarylife.member.repository.MemberRepository;
import com.judy.smartsalarylife.member.request.MemberJoinRequestDto;
import com.judy.smartsalarylife.member.response.MemberJoinResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

}
