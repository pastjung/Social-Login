export const logout = async (token) => {
    const ipAddress = import.meta.env.VITE_IP_ADDRESS;
    const springbootApiPort = import.meta.env.VITE_SPRINGBOOT_HOST_PORT;
    const apiUrl = `http://${ipAddress}:${springbootApiPort}/api/user/logout`;

    try {
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            credentials: 'include',
        });

        if (!response.ok) {
            throw new Error('Failed to Logout');
        }

        return true;  // 로그아웃 성공
    } catch (error) {
        throw new Error('로그아웃 실패: ' + error.message);
    }
};