import React from 'react';

const LoginPage = () => {
    const handleSocialLogin = (provider) => {
        const clientIdMap = {
            kakao: import.meta.env.VITE_KAKAO_REST_API_KEY,
            naver: import.meta.env.VITE_NAVER_CLIENT_ID,
            google: import.meta.env.VITE_GOOGLE_CLIENT_ID,
        };

        const redirectUriMap = {
            kakao: import.meta.env.VITE_KAKAO_REDIRECT_URI,
            naver: import.meta.env.VITE_NAVER_REDIRECT_URI,
            google: import.meta.env.VITE_GOOGLE_REDIRECT_URI,
        };

        // 사용자 인증을 위한 URL: 인증 코드 code 를 요청
        const loginUrl = {
            kakao: `https://kauth.kakao.com/oauth/authorize?client_id=${clientIdMap.kakao}&redirect_uri=${redirectUriMap.kakao}&response_type=code`,
            naver: `https://nid.naver.com/oauth2.0/authorize?client_id=${clientIdMap.naver}&redirect_uri=${redirectUriMap.naver}&response_type=code`,
            google: `https://accounts.google.com/o/oauth2/auth?client_id=${clientIdMap.google}&redirect_uri=${redirectUriMap.google}&response_type=code&scope=openid%20email%20profile`,
        }[provider];

        // 소셜 로그인 페이지로 리디렉션
        window.location.href = loginUrl;
    };

    return (
        <div>
            <h1>로그인 페이지</h1>
            <button onClick={() => handleSocialLogin('kakao')}>카카오 로그인</button>
            <button onClick={() => handleSocialLogin('naver')}>네이버 로그인</button>
            <button onClick={() => handleSocialLogin('google')}>구글 로그인</button>
        </div>
    );
};

export default LoginPage;
