export const pingSpringBoot = async () => {
    const ipAddress =  import.meta.env.VITE_IP_ADDRESS;                    // IP 주소
    const springbootApiPort = import.meta.env.VITE_SPRINGBOOT_HOST_PORT;   // springboot port
    const apiUrl = `http://${ipAddress}:${springbootApiPort}/api/hello`;   // api 호출 URL

    try {
        const response = await fetch(apiUrl);

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        return await response.text();
    } catch (error) {
        throw new Error(error.message);
    }
};