package com.inha.springbootapp.domain.user.service;

import com.inha.springbootapp.domain.user.entity.User;
import com.inha.springbootapp.domain.user.repository.UserRepository;
import com.inha.springbootapp.global.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserInfo(String accessToken) {
        // AccessToken 에서 Claims 추출
        Claims claims = JwtUtil.getUserInfoFromToken(accessToken.replace("Bearer","").trim());

        // Claims 에서 이메일 추출
        String email = claims.get("email", String.class);

        // 이메일을 사용하여 사용자 정보 가져오기
        return userRepository.findByEmail(email);
    }
}
