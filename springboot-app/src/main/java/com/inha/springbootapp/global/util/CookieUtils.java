package com.inha.springbootapp.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value.split(" ")[1].trim());
        cookie.setPath("/");            // 전체 경로에서 사용 가능
        cookie.setHttpOnly(true);       // XSS 방지
//        cookie.setSecure(true);         // HTTPS 사용 시 true
        cookie.setMaxAge(maxAge);       // 유효 시간
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookie = getCookie(request, name);
        if (cookie != null) {
            cookie.setValue(null);                  // 쿠키 값을 null로 설정
            cookie.setPath("/");                    // 쿠키의 경로 설정
            cookie.setMaxAge(0);                    // 쿠키 만료 설정
            cookie.setHttpOnly(true);               // 보안 설정 = 쿠키가 JavaScript 에서 접근되지 않도록 설정
//            cookie.setSecure(request.isSecure());   // HTTPS 에서만 전송되도록 설정 ( 개발 단계에서는 생략 )
            response.addCookie(cookie);             // 수정된 쿠키 추가
        }
    }

    private static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
