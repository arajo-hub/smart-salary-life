package com.judy.smartsalarylife.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long ACCESS_TOKEN_VALIDITY_MILLISECONDS = 30 * 60 * 1000L; // 30분
    private static final long REFRESH_TOKEN_VALIDITY_MILLISECONDS = (60 * 60 * 24 * 7) * 1000L; // 일주일

    public String getUserName(String token){
        String res = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token)
                .getBody().get("userName", String.class);
        return res;
    }

    public boolean checkExpiration(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public String createAccessToken(String username, Collection<? extends GrantedAuthority> authorities){
        Claims claims = Jwts.claims();
        claims.put("userName", username);
        claims.put("authorities", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_MILLISECONDS))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(String username, Collection<? extends GrantedAuthority> authorities){
        Claims claims = Jwts.claims();
        claims.put("userName", username);
        claims.put("authorities", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_MILLISECONDS))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        // 권한 정보를 추출하여 GrantedAuthority 리스트로 변환
        return ((List<String>) claims.get("authorities")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}