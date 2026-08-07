import { reactive } from 'vue'

const TOKEN_KEY = 'social_token'

// 全域共用的登入狀態：所有元件 import authState 拿到的都是同一份
export const authState = reactive({
  token: localStorage.getItem(TOKEN_KEY) || null,
  user: null, // { userId, userName, phone, email, coverImage, biography }
})

export function setToken(token) {
  authState.token = token
  localStorage.setItem(TOKEN_KEY, token)
}

export function setUser(user) {
  authState.user = user
}

export function logout() {
  authState.token = null
  authState.user = null
  localStorage.removeItem(TOKEN_KEY)
}

export function isLoggedIn() {
  return !!authState.token
}

// 判斷某個資源(貼文/留言)是不是目前登入者本人的
export function isOwner(resourceUserId) {
  return !!authState.user && authState.user.userId === resourceUserId
}