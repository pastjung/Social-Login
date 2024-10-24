package com.inha.springbootapp.domain.user.service;

import com.inha.springbootapp.domain.user.entity.User;
import com.inha.springbootapp.domain.user.repository.UserRepository;
import com.inha.springbootapp.global.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "UserService")
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public User getUserInfo(String accessToken) {
        log.info("accessToken: {}", accessToken);

        // AccessToken 에서 Claims 추출
        Claims claims = jwtUtil.getUserInfoFromToken(accessToken.replace("Bearer","").trim());

        // Claims 에서 이메일 추출
        String email = claims.get("email", String.class); // 이메일 클레임 추출

        // 이메일을 사용하여 사용자 정보 가져오기
        return userRepository.findByEmail(email);
    }
}
