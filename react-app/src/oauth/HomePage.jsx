import React, { useEffect, useState } from 'react';

const HomePage = () => {
    const [userInfo, setUserInfo] = useState(null);    // 사용자 정보를 저장하는 상태: 초기값 = null
    const [loading, setLoading] = useState(true);      // 데이터 로딩 상태를 관리하는 상태: 초기값 = true

    useEffect(() => {
        // fetchUserInfo: 비동기 함수 - API 호출을 통해 사용자 정보 불러오기
        const fetchUserInfo = async () => {
            const springbootApiPort = import.meta.env.VITE_SPRINGBOOT_HOST_PORT;    // springboot port
            const apiUrl = `http://localhost:${springbootApiPort}/api/user/userinfo`;

            try {
                // 쿼리 파라미터에서 accessToken 가져오기
                const params = new URLSearchParams(location.search);
                const accessToken = params.get('accessToken');

                if (!accessToken) {
                    throw new Error('Access token not found');
                }

                // 사용자 정보 요청
                const response = await fetch(apiUrl, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${accessToken}`, // accessToken을 Authorization 헤더에 포함
                    },
                    credentials: 'include', // 쿠키를 포함해서 요청
                });
                if (!response.ok) {
                    throw new Error('Failed to fetch user info');
                }
                const data = await response.json();
                setUserInfo(data);
            } catch (error) {
                console.error(error);
            } finally {
                setLoading(false);
            }
        };
        fetchUserInfo();
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
                </div>
            ) : (
                <p>사용자 정보가 없습니다. 로그인해주세요.</p>
            )}
        </div>
    );
};

export default HomePage;