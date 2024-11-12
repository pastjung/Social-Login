import accessToken from '../accessToken';

export const requestAccessTokenFromRefreshToken = async () => {
    const ipAddress =  import.meta.env.VITE_IP_ADDRESS;                         // IP 주소
    const springbootApiPort = import.meta.env.VITE_SPRINGBOOT_HOST_PORT;        // springboot port
    const apiUrl = `http://${ipAddress}:${springbootApiPort}/api/auth/token`;   // 재발급 API 경로

    try {
        const response = await fetch(apiUrl, {
            method: 'GET',
            credentials: 'include', // 쿠키를 포함하여 요청
        });

        if (!response.ok) {
            throw new Error('Failed to refresh access token');
        }

        const newToken = await response.text();
        accessToken.setToken(newToken); // 새로운 액세스 토큰 저장
        return newToken;
    } catch (error) {
        // 리프레시 토큰이 없거나 만료된 경우 로딩 완료
        console.error(error);
        setLoading(false);
        throw error;
    }
};