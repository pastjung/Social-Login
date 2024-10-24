package com.inha.springbootapp.domain.oauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inha.springbootapp.domain.oauth.dto.SocialLoginUserDto;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class NaverAuthUtil extends AuthUtil {

    @Override
    protected String getTokenEndpoint() {
        return "https://nid.naver.com/oauth2.0/token";
    }

    @Override
    protected String getUserInfoEndpoint() {
        return "https://openapi.naver.com/v1/nid/me";
    }

    @Override
    protected void populateAccessTokenBody(MultiValueMap<String, String> body, String code) {
        body.add("grant_type", "authorization_code");
        body.add("client_id", dotenv.get("NAVER_CLIENT_ID"));
        body.add("client_secret", dotenv.get("NAVER_CLIENT_SECRET"));
        body.add("redirect_uri", dotenv.get("NAVER_REDIRECT_URI"));
        body.add("code", code);
    }

    @Override
    protected SocialLoginUserDto parseUserData(String responseBody, String loginType) throws JsonProcessingException {
        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
        JsonNode responseNode = jsonNode.get("response");
        String socialLoginId = responseNode.has("id") ? responseNode.get("id").asText() : null;
        String nickname = responseNode.has("name") ? responseNode.get("name").asText() : null;
        String email = responseNode.has("email") ? responseNode.get("email").asText() : null;

        return SocialLoginUserDto.builder()
                .nickname(nickname)
                .email(email)
                .loginType(loginType)
                .socialLoginId(socialLoginId)
                .build();
    }
}
