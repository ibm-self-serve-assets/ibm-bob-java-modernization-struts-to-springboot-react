import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    proxy: {
      // user-service
      '/api/users': {
        target: 'http://localhost:8081',
        changeOrigin: true,
      },
      // vehicle-service
      '/api/vehicles': {
        target: 'http://localhost:8082',
        changeOrigin: true,
      },
      // premium-service
      '/api/quotes': {
        target: 'http://localhost:8083',
        changeOrigin: true,
      },
    },
  },
});
