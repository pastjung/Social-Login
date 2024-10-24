import React, { useState } from 'react';

const LoginPage = () => {
    const handleSocialLogin = (provider) => {
        const kakaoClientId = import.meta.env.VITE_KAKAO_REST_API_KEY;      // 카카오 개발자에서 발급받은 REST API 키
        const kakaoRedirectUri = import.meta.env.VITE_KAKAO_REDIRECT_URI;   // 카카오 리다이렉트 URI
        const naverClientId = import.meta.env.VITE_NAVER_CLIENT_ID;         // 네이버 개발자에서 발급받은 REST API 키
        const naverRedirectUri = import.meta.env.VITE_NAVER_REDIRECT_URI;   // 네이버 리다이렉트 URI
        const googleClientId = import.meta.env.VITE_GOOGLE_CLIENT_ID;       // 구글 개발자에서 발급받은 REST API 키
        const googleRedirectUri = import.meta.env.VITE_GOOGLE_REDIRECT_URI; // 구글 리다이렉트 URI

        // 사용자 인증을 위한 URL: 인증 코드 code 를 요청
        let loginUrl;
        switch (provider) {
            case 'kakao':
                loginUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${kakaoClientId}&redirect_uri=${kakaoRedirectUri}&response_type=code`;
                break;
            case 'naver':
                loginUrl = `https://nid.naver.com/oauth2.0/authorize?client_id=${naverClientId}&redirect_uri=${naverRedirectUri}&response_type=code`;
                break;
            case 'google':
                loginUrl = `https://accounts.google.com/o/oauth2/auth?client_id=${googleClientId}&redirect_uri=${googleRedirectUri}&response_type=code&scope=openid%20email%20profile`;
                break;
            default:
                return;
        }
        window.location.href = loginUrl; // 소셜 로그인 페이지로 리디렉션
    };

    const [message, setMessage] = useState('')

    const pingSpringBoot = async () => {
        const springbootApiPort = import.meta.env.VITE_SPRINGBOOT_HOST_PORT;    // springboot port
        const apiUrl = `http://localhost:${springbootApiPort}/api/hello`;

        try {
          const response = await fetch(apiUrl);
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          const data = await response.text();
          setMessage(data);
        } catch (error) {
          setMessage('Error: ' + error.message);
        }
    };

    return (
        <div>
            <h1>로그인 페이지</h1>
            <button onClick={() => pingSpringBoot()}>Ping: Spring Boot</button>
            <p>{message}</p>
            <button onClick={() => handleSocialLogin('kakao')}>카카오 로그인</button>
            <button onClick={() => handleSocialLogin('naver')}>네이버 로그인</button>
            <button onClick={() => handleSocialLogin('google')}>구글 로그인</button>
        </div>
    );
};

export default LoginPage;
