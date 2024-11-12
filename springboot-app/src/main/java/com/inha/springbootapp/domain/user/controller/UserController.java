package com.inha.springbootapp.domain.user.controller;

import com.inha.springbootapp.domain.user.entity.User;
import com.inha.springbootapp.domain.user.service.UserService;
import com.inha.springbootapp.global.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String accessToken) {
        User user = userService.getUserInfo(accessToken);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, "refreshToken"); // 리프레시 토큰 쿠키 삭제
        return ResponseEntity.ok().build();
    }
}
