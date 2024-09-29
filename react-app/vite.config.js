import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    host: true, // 외부에서 접근 가능하도록 설정
    port: process.env.VITE_REACT_SERVER_PORT, // 환경 변수에서 포트 읽기
  },
  plugins: [react()],
})
