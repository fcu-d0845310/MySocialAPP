package com.syh.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 注意：故意不放 password 欄位，註冊完不能把密碼(即使是加密過的)回傳給前端
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private Long userId;
    private String userName;
    private String phone;
    private String email;
    private String coverImage;
    private String biography;
}