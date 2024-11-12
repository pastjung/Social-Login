import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import accessToken from '../accessToken';

const ConnectPage = () => {
    const navigate = useNavigate(); // useNavigate 훅 사용

    useEffect(() => {
        const params = new URLSearchParams(location.search);
        const token = params.get('accessToken');

        if (token) {
            accessToken.setToken(token);  // 액세스 토큰을 메모리에 저장
            navigate('/home');  // React Router를 사용하여 페이지 이동
        } else {
            alert("잘못된 접근 방법")
            navigate('/');      // 홈으로 리디렉션
        }
    }, [navigate]);

    return (
        <div>
            <h1>메인 페이지로 연결 중..</h1>
        </div>
    );
};

export default ConnectPage;