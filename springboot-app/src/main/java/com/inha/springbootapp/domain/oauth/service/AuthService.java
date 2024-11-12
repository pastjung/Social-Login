package com.inha.springbootapp.domain.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inha.springbootapp.domain.oauth.dto.SocialLoginUserDto;
import com.inha.springbootapp.domain.oauth.util.GoogleAuthUtil;
import com.inha.springbootapp.domain.oauth.util.KakaoAuthUtil;
import com.inha.springbootapp.domain.oauth.util.NaverAuthUtil;
import com.inha.springbootapp.domain.user.entity.User;
import com.inha.springbootapp.domain.user.repository.UserRepository;
import com.inha.springbootapp.global.util.CookieUtils;
import com.inha.springbootapp.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final KakaoAuthUtil kakaoAuthUtil;
    private final NaverAuthUtil naverAuthUtil;
    private final GoogleAuthUtil googleAuthUtil;

    public String callback(String loginType, String code, HttpServletResponse response) throws JsonProcessingException {

        String accessToken;
        SocialLoginUserDto userInfoResponse;
        switch (loginType) {
            case "kakao":
                // 1. "인가 코드 code"로 "Access Token" 요청
                accessToken = kakaoAuthUtil.getAccessToken(code);
                // 2. Access Token 사용 -> 소셜로그인 API 호출 -> 사용자 정보 요청
                userInfoResponse = kakaoAuthUtil.callUserData(loginType, accessToken);
                break;
            case "naver":
                accessToken = naverAuthUtil.getAccessToken(code);
                userInfoResponse = naverAuthUtil.callUserData(loginType, accessToken);
                break;
            case "google":
                accessToken = googleAuthUtil.getAccessToken(code);
                userInfoResponse = googleAuthUtil.callUserData(loginType, accessToken);
                break;
            default:
                return null;
        }

        // 3. 불러온 사용자 정보를 DB에 저장
        User user = saveUser(loginType, userInfoResponse);

        // 4. 가져온 정보를 가지고 JWT 토큰 생성 및 반환 -> 액세스 토큰 = 메모리, 리프레시 토큰 = 쿠키
        return createAndReturnToken(user, response);
    }

    private User saveUser(String loginType, SocialLoginUserDto userInfoResponse){

        User saveduser = userRepository.findBySocialLoginId(userInfoResponse.getSocialLoginId());
        if (saveduser == null) {
            // 중복 여부 확인
            User user = User.builder()
                    .nickname(userInfoResponse.getNickname())
                    .email(userInfoResponse.getEmail())
                    .loginType(loginType)
                    .socialLoginId(userInfoResponse.getSocialLoginId())
                    .build();
            return userRepository.save(user);
        }
        return saveduser;
    }

    private String createAndReturnToken(User user, HttpServletResponse response){
        // 토큰 만료 시간
        int ACCESS_TOKEN_TIME = 60 * 60 * 1000;        // 60분
        int REFRESH_TOKEN_TIME = 24 * 60 * 60 * 1000;  // 1일

        String accessToken = JwtUtil.createJWT(user, ACCESS_TOKEN_TIME);
        String refreshToken = JwtUtil.createJWT(user, REFRESH_TOKEN_TIME);

        CookieUtils.addCookie(response,"refreshToken", refreshToken, REFRESH_TOKEN_TIME);

        return accessToken;
    }
}
