import React from 'react';
import accessToken from '../accessToken';
import { logout } from '../apis/logout';

const Logout = () => {
    const callAPI = async () => {
        const token = accessToken.getToken();

        if (!token) {
            alert('로그인 상태가 아닙니다.');
            return;
        }

        try {
            const result = await logout(token);

            if (result) {
                window.location.href = '/';
                alert('로그아웃 성공');
            }
        } catch (error) {
            alert(`로그아웃 실패: ${error.message}`);
            console.error(error);
        }
    };

    return (
        <div>
            <button onClick={callAPI}>로그아웃</button>
        </div>
    );
};

export default Logout;