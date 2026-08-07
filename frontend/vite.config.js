import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173,
    proxy: {
      // 前端呼叫 /api/... 會被轉發到後端 Spring Boot (localhost:8080)
      // 這樣開發時不用讓後端另外開 CORS 設定
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})