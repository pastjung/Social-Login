package com.inha.springbootapp.domain.oauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inha.springbootapp.domain.oauth.dto.SocialLoginUserDto;
import com.inha.springbootapp.global.util.JwtUtil;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
public abstract class AuthUtil {
    protected static final RestTemplate restTemplate = new RestTemplate();
    protected static final Dotenv dotenv = Dotenv.load();

    protected abstract String getTokenEndpoint();
    protected abstract String getUserInfoEndpoint();

    protected abstract void populateAccessTokenBody(MultiValueMap<String, String> body, String code);
    protected abstract SocialLoginUserDto parseUserData(String responseBody, String loginType) throws JsonProcessingException;

    public String getAccessToken(String code) throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString(getTokenEndpoint())
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        populateAccessTokenBody(body, code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    public SocialLoginUserDto callUserData(String loginType, String accessToken) throws JsonProcessingException {
        URI uri = UriComponentsBuilder
                .fromUriString(getUserInfoEndpoint())
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JwtUtil.BEARER_PREFIX + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        return parseUserData(response.getBody(), loginType);
    }
}