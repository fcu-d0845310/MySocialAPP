import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import { authState, setUser, logout } from './stores/auth'
import { me } from './api/auth'

const app = createApp(App)
app.use(router)
app.mount('#app')

// 重新整理頁面後，如果本機有存 token，就用它換回登入者資訊，維持登入狀態
if (authState.token) {
  me()
    .then((user) => setUser(user))
    .catch(() => logout()) // token 過期或無效，直接登出
}
