package com.syh.social.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "Username cannot be null")
    private String userName;

    @NotBlank(message = "Phone cannot be null")
    private String phone;

    @NotBlank(message = "password cannot nbe null")
    private String password;

    private String email;

    private String coverImage;

    private String biography;
}
