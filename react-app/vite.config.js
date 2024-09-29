import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    host: true, // 외부에서 접근 가능하도록 설정
    port: process.env.VITE_REACT_SERVER_PORT, // 환경 변수에서 포트 읽기
    watch: {
      usePolling: true, // Polling 사용 : 주기적으로 polling 방식으로 파일 시스템을 확인하여 변경 사항을 감지 ( docker-compose.yml의 volumes와 함께 설정 )
    },
  },
  plugins: [react()],
})
