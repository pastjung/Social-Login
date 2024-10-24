package com.inha.springbootapp.domain.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginUserDto {
    private String nickname;
    private String email;
    private String loginType;
    private String socialLoginId;
}
