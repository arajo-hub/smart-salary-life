package com.judy.smartsalarylife.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.judy.smartsalarylife.common.response.CommonExceptionResponse;
import com.judy.smartsalarylife.member.response.MemberLoginResponseDto;
import com.judy.smartsalarylife.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    private final JwtUtil jwtUtil;

    public JsonUsernamePasswordAuthenticationFilter(String defaultFilterProcessesUrl, JwtUtil jwtUtil, ObjectMapper objectMapper) {
        super();
        setFilterProcessesUrl(defaultFilterProcessesUrl); // 로그인 URL 설정
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        if (request.getContentType().equals("application/json")) {
            Map<String, String> credentials = null;
            try {
                credentials = objectMapper.readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String email = credentials.get("email");
            String password = credentials.get("password");

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);

            return this.getAuthenticationManager().authenticate(authRequest);
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 로그인에 성공한 유저
        final UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();

        // response body에 넣을 값 생성
        MemberLoginResponseDto result = MemberLoginResponseDto.builder()
                .email(userDetails.getUsername())
                .accessToken(jwtUtil.createAccessToken(userDetails.getUsername(), authorities))
                .refreshToken(jwtUtil.createRefreshToken(userDetails.getUsername(), authorities))
                .build();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), result);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        // 로그인 실패 시 처리 로직
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // response body에 넣을 값 생성
        CommonExceptionResponse commonResponse = CommonExceptionResponse.builder().statusCode(HttpStatus.UNAUTHORIZED.value()).message("인증에 실패했습니다.").build();
        new ObjectMapper().writeValue(response.getOutputStream(), commonResponse);
    }

}