package com.syh.social.service.impl;

import com.syh.social.security.UserDetail;
import com.syh.social.security.UserDetailService;
import com.syh.social.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;

    @Value("${jwt.secret}")
    private String secretKey;
    private final Long jwtExpireMs = 86400000L;


    @Override
    public UserDetails authenticate(String phone, String password) {
        //利用authenticationManager把phone、password去驗證，如果是valid就會回傳Authentication，沒有就會丟出例外(如BadCredentialsException)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(phone, password)
        );
        return userDetailService.loadUserByUsername(phone);
    }

    @Override
    public String generateToken(UserDetails userDetails){
        Map<String, Objects> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpireMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public UserDetails validToken(String token){
        String username = extractUsername(token);
        return userDetailService.loadUserByUsername(username);
    }

    private String extractUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();//前面我們把phone放到subject裡面
    }


    private Key getSigningKey(){
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
