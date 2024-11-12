import React, { useEffect, useState } from 'react';
import { fetchUserInfo } from '../apis/fetchUserInfo';
import PingSpringboot from '../buttons/PingAccessToken';
import Logout from '../buttons/Logout';
import accessToken from '../accessToken';

const HomePage = () => {
    const [userInfo, setUserInfo] = useState(null);     // 사용자 정보를 저장하는 상태: 초기값 = null
    const [loading, setLoading] = useState(true);       // 데이터 로딩 상태를 관리하는 상태: 초기값 = true

    useEffect(() => {
        const token = accessToken.getToken();

        const callAPI = async () => {
            try {
                const data = await fetchUserInfo(token);  // 분리된 함수 호출
                setUserInfo(data);
            } catch (error) {
                console.error(error);
            } finally {
                setLoading(false);
            }
        };

        callAPI();
    }, []);

    return (
        <div>
            <h1>홈 화면</h1>
            {loading ? (
                <p>로딩 중...</p>
            ) : userInfo ? (
                <div>
                    <h2>안녕하세요, {userInfo.nickname}님!</h2>
                    <p>Email: {userInfo.email}</p>
                    <p>로그인 유형: {userInfo.loginType}</p>
                    <p>소셜로그인 ID: {userInfo.socialLoginId}</p>
                    <PingSpringboot />
                    <Logout />
                </div>
            ) : (
                <p>사용자 정보가 없습니다. 로그인해주세요.</p>
            )}
        </div>
    );
};

export default HomePage;