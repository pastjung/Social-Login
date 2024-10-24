package com.inha.springbootapp.domain.oauth.controller;

import com.inha.springbootapp.domain.oauth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/callback")
    public void callback(@RequestParam String loginType ,@RequestParam String code, HttpServletResponse response) throws IOException {
        String accessToken = authService.callback(loginType, code, response);
        String redirectUrl = "http://localhost:3001/Home?accessToken=" + accessToken;
        response.sendRedirect(redirectUrl);
    }
}
