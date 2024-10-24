package com.inha.springbootapp.domain.oauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inha.springbootapp.domain.oauth.dto.SocialLoginUserDto;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class GoogleAuthUtil extends AuthUtil {

    @Override
    protected String getTokenEndpoint() {
        return "https://oauth2.googleapis.com/token";
    }

    @Override
    protected String getUserInfoEndpoint() {
        return "https://www.googleapis.com/oauth2/v3/userinfo";
    }

    @Override
    protected void populateAccessTokenBody(MultiValueMap<String, String> body, String code) {
        body.add("grant_type", "authorization_code");
        body.add("client_id", dotenv.get("GOOGLE_CLIENT_ID"));
        body.add("client_secret", dotenv.get("GOOGLE_CLIENT_SECRET"));
        body.add("redirect_uri", dotenv.get("GOOGLE_REDIRECT_URI"));
        body.add("code", code);
    }

    @Override
    protected SocialLoginUserDto parseUserData(String responseBody, String loginType) throws JsonProcessingException {
        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
        String socialLoginId = jsonNode.get("sub").asText();
        String nickname = jsonNode.get("name").asText();
        String email = jsonNode.get("email").asText();

        return SocialLoginUserDto.builder()
                .nickname(nickname)
                .email(email)
                .loginType(loginType)
                .socialLoginId(socialLoginId)
                .build();
    }
}
