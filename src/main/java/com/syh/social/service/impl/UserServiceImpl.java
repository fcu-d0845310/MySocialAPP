package com.syh.social.service.impl;

import com.syh.social.dto.RegisterRequest;
import com.syh.social.dto.RegisterResponse;
import com.syh.social.entity.User;
import com.syh.social.mapper.UserMapper;
import com.syh.social.repository.UserRepository;
import com.syh.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public RegisterResponse UserRegister(RegisterRequest registerRequest) {

        if(userRepository.findByPhone(registerRequest.getPhone()).isPresent()){
            throw new IllegalArgumentException("User already exists with this phone number");
        }

        User user = User.builder()
                .userName(registerRequest.getUserName())
                .phone(registerRequest.getPhone())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .coverImage(registerRequest.getCoverImage())
                .biography(registerRequest.getBiography())
                .build();
        Long userId = userRepository.userRegister(user);
        user.setUserId(userId);
        return userMapper.toRegisterResponse(user);
    }


}
