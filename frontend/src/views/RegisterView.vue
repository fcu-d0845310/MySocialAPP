<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '../api/auth'

const router = useRouter()
const userName = ref('')
const phone = ref('')
const password = ref('')
const email = ref('')
const biography = ref('')
const error = ref('')
const success = ref(false)
const loading = ref(false)

async function handleSubmit() {
  error.value = ''
  loading.value = true
  try {
    await register({
      userName: userName.value,
      phone: phone.value,
      password: password.value,
      email: email.value || null,
      biography: biography.value || null,
    })
    success.value = true
    setTimeout(() => router.push('/login'), 1200)
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="card" style="max-width: 400px; margin: 40px auto;">
    <h2>註冊帳號</h2>
    <div v-if="error" class="error-box">{{ error }}</div>
    <div v-if="success" class="error-box" style="color: #2c8a3d; background: rgba(44,138,61,0.1); border-color: rgba(44,138,61,0.3);">
      註冊成功，正在跳轉到登入頁...
    </div>
    <form @submit.prevent="handleSubmit" v-if="!success">
      <div class="form-group">
        <label>使用者名稱</label>
        <input v-model="userName" type="text" required />
      </div>
      <div class="form-group">
        <label>手機號碼</label>
        <input v-model="phone" type="tel" required />
      </div>
      <div class="form-group">
        <label>密碼</label>
        <input v-model="password" type="password" required />
      </div>
      <div class="form-group">
        <label>Email（選填）</label>
        <input v-model="email" type="email" />
      </div>
      <div class="form-group">
        <label>自我介紹（選填）</label>
        <textarea v-model="biography"></textarea>
      </div>
      <button type="submit" :disabled="loading" style="width: 100%;">
        {{ loading ? '註冊中...' : '註冊' }}
      </button>
    </form>
    <p class="muted" style="margin-top: 12px;">
      已經有帳號了？<router-link to="/login">去登入</router-link>
    </p>
  </div>
</template>
