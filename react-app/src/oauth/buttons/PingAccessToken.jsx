import React from 'react';
import { fetchUserInfo } from '../apis/fetchUserInfo';

const PingAccessToken = () => {
    const callAPI  = async () => {
        const params = new URLSearchParams(location.search);
        const accessToken = params.get('accessToken');

        if (!accessToken) {
            throw new Error('Access token not found');
        }

        try {
            const data = await fetchUserInfo(accessToken);
            alert(`안녕하세요, ${data.nickname}님!\nEmail: ${data.email}\n로그인 유형: ${data.loginType}\n소셜로그인 ID: ${data.socialLoginId}`);
        } catch (error) {
            alert('사용자 정보를 가져오는 데 오류가 발생했습니다.');
            console.error(error);
        }
    };

    return (
        <div>
            <button onClick={callAPI}>Ping: 사용자 정보 보기</button>
        </div>
    );
};

export default PingAccessToken;