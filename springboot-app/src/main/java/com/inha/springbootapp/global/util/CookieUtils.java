package com.inha.springbootapp.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value.split(" ")[1].trim());
        cookie.setPath("/");            // 전체 경로에서 사용 가능
        cookie.setHttpOnly(true);       // XSS 방지
        cookie.setSecure(true);         // HTTPS 사용 시 true
        cookie.setMaxAge(maxAge);       // 유효 시간
        response.addCookie(cookie);
    }
}
