package com.syh.social.service;

import com.syh.social.dto.RegisterRequest;
import com.syh.social.dto.RegisterResponse;

public interface UserService {
    RegisterResponse UserRegister(RegisterRequest user);
}
