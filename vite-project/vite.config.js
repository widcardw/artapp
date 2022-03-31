import {defineConfig, loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'
import path from "path";

// https://vitejs.dev/config/
export default (command, mode) => {
  return defineConfig({
    plugins: [vue()],
    resolve: {
      alias: {
        '@': path.join(__dirname, 'src'),  // 设置别名
      }
    },
    server: {
      host: 'localhost',
      port: Number(loadEnv(mode, process.cwd()).VITE_APP_PORT),
      strictPort: true, // 端口被占用直接退出
      https: false,
      open: true,// 在开发服务器启动时自动在浏览器中打开应用程序
      proxy: {
        // 字符串简写写法
        // '/foo': '',
        // 选项写法
        '/api': {
          target: 'http://localhost:8081',
          changeOrigin: true,
          rewrite: path => path.replace(/^\/api/, '')
        }
      },
      hmr: {
        overlay: false // 屏蔽服务器报错
      }
    },
    build: {
      chunkSizeWarningLimit: 1500, // 分块打包，分解块，将大块分解成更小的块
      rollupOptions: {
        output: {
          manualChunks(id) {
            if (id.includes('node_modules')) {
              return id.toString().split('node_modules/')[1].split('/')[0].toString();
            }
          }
        }
      }
    },
    publicPath: process.env.NODE_ENV === 'production' ? './' : '/',
  })
}
