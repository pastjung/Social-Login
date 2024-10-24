package com.inha.springbootapp.domain.oauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inha.springbootapp.domain.oauth.dto.SocialLoginUserDto;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class KakaoAuthUtil extends AuthUtil {

    @Override
    protected String getTokenEndpoint() {
        return "https://kauth.kakao.com/oauth/token";
    }

    @Override
    protected String getUserInfoEndpoint() {
        return "https://kapi.kakao.com/v2/user/me";
    }

    @Override
    protected void populateAccessTokenBody(MultiValueMap<String, String> body, String code) {
        body.add("grant_type", "authorization_code");
        body.add("client_id", dotenv.get("KAKAO_REST_API_KEY"));
        body.add("client_secret", dotenv.get("KAKAO_CLIENT_SECRET"));
        body.add("redirect_uri", dotenv.get("KAKAO_REDIRECT_URI"));
        body.add("code", code);
    }

    @Override
    protected SocialLoginUserDto parseUserData(String responseBody, String loginType) throws JsonProcessingException {
        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
        String socialLoginId = jsonNode.get("id").asText();
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String email = jsonNode.get("kakao_account").get("email").asText();

        return SocialLoginUserDto.builder()
                .nickname(nickname)
                .email(email)
                .loginType(loginType)
                .socialLoginId(socialLoginId)
                .build();
    }
}
