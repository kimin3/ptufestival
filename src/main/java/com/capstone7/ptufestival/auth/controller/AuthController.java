// controller/AuthController.java
package com.capstone7.ptufestival.auth.controller;

import com.capstone7.ptufestival.auth.dto.LoginRequestDto;
import com.capstone7.ptufestival.auth.dto.LoginResponseDto;
import com.capstone7.ptufestival.auth.dto.RegisterRequestDto;
import com.capstone7.ptufestival.auth.jwt.JwtUtil;
import com.capstone7.ptufestival.auth.model.RefreshToken;
import com.capstone7.ptufestival.auth.model.User;
import com.capstone7.ptufestival.auth.service.RefreshTokenService;
import com.capstone7.ptufestival.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "인증", description = "회원가입, 로그인, 토큰 재발급 API")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "회원가입", description = "username, password, name을 입력해 회원가입합니다.")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request) {
        userService.register(request);
        return ResponseEntity.ok("회원가입 완료");
    }

    @Operation(summary = "로그인", description = "username, password를 입력하면 accessToken과 refreshToken이 발급됩니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @Operation(summary = "AccessToken 재발급", description = "RefreshToken을 이용해 새로운 AccessToken을 발급합니다.")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshTokenStr = request.get("refreshToken");

        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenStr);
        if (!refreshTokenService.isValid(refreshToken)) {
            return ResponseEntity.badRequest().body("Refresh token has expired");
        }

        User user = refreshToken.getUser();
        String newAccessToken = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken
        ));
    }
}
