package com.judy.smartsalarylife.filter;

import com.judy.smartsalarylife.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.info("authorization : " + authorization);

        //token 안
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            if (request.getRequestURI().contains("/members/login") || request.getRequestURI().equals("/members")) {
                filterChain.doFilter(request, response);
                return;
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"code\":\"401\", \"message\":\"토큰이 없는 요청입니다.\"}");
                return;
            }
        }

        //token 꺼내기
        String token = authorization.split(" ")[1];
        logger.info("token : " + token);

        //token Expired되었는지
        try {
            jwtUtil.checkExpiration(token);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에 대한 응답 커스텀
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"code\":\"401\", \"message\":\"토큰이 만료되었습니다.\"}");
            return;
        }

        //username token에서 꺼내기
        String userName = jwtUtil.getUserName(token);
        Collection<? extends GrantedAuthority> authorities = jwtUtil.getAuthorities(token);
        logger.info("username: " + userName);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, null, authorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}