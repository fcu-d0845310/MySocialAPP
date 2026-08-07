package com.syh.social.repository.impl;

import com.syh.social.entity.User;
import com.syh.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcClient jdbcClient;


    @Override
    public Long userRegister(User user) {
            return jdbcClient
                    .sql("SELECT sp_register(:userName, :phone, :password, :email, :biography, :coverImage)")
                    .param("userName", user.getUserName())
                    .param("phone", user.getPhone())
                    .param("password", user.getPassword())
                    .param("email", user.getEmail())
                    .param("biography", user.getBiography())
                    .param("coverImage", user.getCoverImage())
                    .query(Long.class)
                    .single();
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return jdbcClient
                .sql("SELECT * FROM sp_find_user_by_phone(:phone)")
                .param("phone", phone)
                .query(User.class)
                .optional();
    }
}
