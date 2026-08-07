import request from './http'

export function register(payload) {
  // payload: { userName, phone, password, email?, coverImage?, biography? }
  return request('/auth/register', { method: 'POST', body: payload })
}

export function login(phone, password) {
  return request('/auth/login', { method: 'POST', body: { phone, password } })
}

// 用目前的 token 查登入者資訊(userId、userName...)
export function me() {
  return request('/auth/me', { method: 'GET', auth: true })
}