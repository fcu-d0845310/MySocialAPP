package com.syh.social.service;

import com.syh.social.security.UserDetail;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(String phone, String password);
    String generateToken(UserDetails userDetails);
    UserDetails validToken(String token);
}
