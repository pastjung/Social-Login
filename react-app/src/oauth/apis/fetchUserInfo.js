// fetchUserInfo: 비동기 함수 - API 호출을 통해 사용자 정보 불러오기
export const fetchUserInfo = async (accessToken) => {
    const ipAddress =  import.meta.env.VITE_IP_ADDRESS;                            // IP 주소
    const springbootApiPort = import.meta.env.VITE_SPRINGBOOT_HOST_PORT;           // springboot port
    const apiUrl = `http://${ipAddress}:${springbootApiPort}/api/user/userinfo`;   // api 호출 URL

    try {
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

        return await response.json();
    } catch (error) {
        console.error(error);
        throw error;    // 오류를 던져서 호출한 곳에서 처리하도록 설정
    }
};