package com.judy.smartsalarylife.member.service;

import com.judy.smartsalarylife.member.domain.Member;
import com.judy.smartsalarylife.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Member not found with email: " + email)
        );

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        return new org.springframework.security.core.userdetails.User(
                member.getEmail(),
                member.getEncryptedPassword(),
                Collections.singletonList(authority)
        );
    }
}
