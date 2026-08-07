<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login, me } from '../api/auth'
import { setToken, setUser } from '../stores/auth'

const router = useRouter()
const phone = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)

async function handleSubmit() {
  error.value = ''
  loading.value = true
  try {
    const { token } = await login(phone.value, password.value)
    setToken(token)
    const user = await me()
    setUser(user)
    router.push('/')
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="card" style="max-width: 360px; margin: 40px auto;">
    <h2>登入</h2>
    <div v-if="error" class="error-box">{{ error }}</div>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label>手機號碼</label>
        <input v-model="phone" type="tel" required />
      </div>
      <div class="form-group">
        <label>密碼</label>
        <input v-model="password" type="password" required />
      </div>
      <button type="submit" :disabled="loading" style="width: 100%;">
        {{ loading ? '登入中...' : '登入' }}
      </button>
    </form>
    <p class="muted" style="margin-top: 12px;">
      還沒有帳號？<router-link to="/register">去註冊</router-link>
    </p>
  </div>
</template>
