package com.syh.social.repository;

import com.syh.social.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository {
    Long userRegister(User user);
    Optional<User> findByPhone(String phone);
}
