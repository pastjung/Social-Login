import React, { useState } from 'react';

const LoginPage = () => {
    const [message, setMessage] = useState('')

    const pingSpringBoot = async () => {
        const springbootApiPort = import.meta.env.VITE_SPRINGBOOT_HOST_PORT;    // springboot port
        const apiUrl = `http://localhost:${springbootApiPort}/api/hello`;

        try {
          const response = await fetch(apiUrl);
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          const data = await response.text();
          setMessage(data);
        } catch (error) {
          setMessage('Error: ' + error.message);
        }
    };

    return (
        <div>
            <h1>로그인 페이지</h1>
            <button onClick={() => pingSpringBoot()}>Ping: Spring Boot</button>
            <p>{message}</p>
        </div>
    );
};

export default LoginPage;
