package com.syh.social.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;


@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private Long userId;
    private String userName;
    private String phone;
    private String email;
    private String password;
    private String coverImage;
    private String biography;
}
