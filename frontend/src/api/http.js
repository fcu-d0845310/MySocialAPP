import { authState, logout } from '../stores/auth'

// 統一處理：帶 baseURL、自動加上 Authorization header、統一錯誤格式
async function request(path, { method = 'GET', body, auth = false } = {}) {
  const headers = { 'Content-Type': 'application/json' }

  if (auth && authState.token) {
    headers['Authorization'] = `Bearer ${authState.token}`
  }

  const res = await fetch(`/api${path}`, {
    method,
    headers,
    body: body ? JSON.stringify(body) : undefined,
  })

  // 204 No Content 沒有 body 可以解析
  if (res.status === 204) {
    return null
  }

  let data = null
  try {
    data = await res.json()
  } catch {
    // 沒有 JSON body (例如某些 403/空回應)，忽略即可
  }

  if (!res.ok) {
    // token 失效或未登入就打了需要登入的 API，直接登出讓使用者重新登入
    if (res.status === 401 || res.status === 403) {
      logout()
    }
    let message = data?.message || `發生錯誤 (HTTP ${res.status})`
    // 欄位驗證錯誤(errors: { fieldName: "訊息" })，串成好讀的一行
    if (data?.errors) {
      message = Object.values(data.errors).join('; ')
    }
    throw new Error(message)
  }

  return data
}

export default request