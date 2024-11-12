package com.inha.springbootapp.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration              // Bean 사용 정의
@EnableWebSecurity          // Spring Security 사용 정의
@RequiredArgsConstructor    // 모든 필드를 가지는 생성자 생성
public class WebSecurityConfig {

    // Password Encoder : 기존 Default 로 생성된 Password Encoder 대신 BCryptPasswordEncoder 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)                   // 기본 제공되는 폼 로그인 기능을 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)                   // HTTP Basic 인증을 비활성화 -> 기본 인증을 사용하지 않도록 설정하여 보안을 강화
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 서버가 클라이언트의 세션을 저장하지 않으며,
                // 매 요청마다 클라이언트가 필요한 인증 정보를 포함해야 함
                // 주로 RESTful API와 같이 상태를 유지하지 않는 어플리케이션에서 사용
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()                                          // resources 접근 허용 설정, permitAll() : 접근 허가
                        .requestMatchers("/").permitAll()                   // 메인 페이지 접근 허용
//                        .requestMatchers("/api/user/**").hasRole("USER")    // user 접근 가능 페이지
//                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  // admin 접근 가능 페이지
                        .requestMatchers("/**").permitAll()                 // 모든 페이지 접근 허가 ( 임시 )
                        // .anyRequest().authenticated()                         // anyRequest() : 위 설정 이외 모두, authenticated() : jwt 인증 필요함, // logout 기능을 위해 제거 -> permitAll()과 authenticated()를 함께 사용하는 것은 비추천
                );

        return http.build();
    }
}
