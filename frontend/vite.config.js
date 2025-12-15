import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// Backend proxy för lokalt läge
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
      }
    }
  }
})
