package com.syh.social.controller;


import com.syh.social.dto.LoginRequest;
import com.syh.social.dto.LoginResponse;
import com.syh.social.dto.RegisterRequest;
import com.syh.social.dto.RegisterResponse;
import com.syh.social.mapper.UserMapper;
import com.syh.social.security.UserDetail;
import com.syh.social.service.AuthenticationService;
import com.syh.social.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @PostMapping(path = "/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = userService.UserRegister(registerRequest);
        return ResponseEntity.ok(response);
    }

    // 給前端在重新整理頁面／App 啟動時，用 token 換回目前登入者的資訊(userId、userName...)
    // principal 裡的 User 是 JwtAuthenticationFilter 驗證 token 時就查好的
    @GetMapping(path = "/me")
    public ResponseEntity<RegisterResponse> me(@AuthenticationPrincipal UserDetail principal) {
        RegisterResponse response = userMapper.toRegisterResponse(principal.getUser());
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        UserDetails userDetails = authenticationService.authenticate(loginRequest.getPhone(), loginRequest.getPassword());
        //利用authenticationService把抓出來的phone、password去驗證，如果是valid 就能用userdetails，沒有就會丟出例外
        String token = authenticationService.generateToken(userDetails);
        LoginResponse response = LoginResponse.builder()
                .token(token)
                .build();
        return ResponseEntity.ok(response);
    }
}
