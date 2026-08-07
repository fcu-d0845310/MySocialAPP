<script setup>
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { authState, logout } from './stores/auth'

const router = useRouter()

function handleLogout() {
  logout()
  router.push('/')
}
</script>

<template>
  <div class="navbar">
    <RouterLink to="/" class="brand">📱 Social</RouterLink>
    <div class="nav-links">
      <template v-if="authState.user">
        <span class="muted">哈囉，{{ authState.user.userName }}</span>
        <button class="secondary" @click="handleLogout">登出</button>
      </template>
      <template v-else>
        <RouterLink to="/login"><button class="secondary">登入</button></RouterLink>
        <RouterLink to="/register"><button>註冊</button></RouterLink>
      </template>
    </div>
  </div>

  <RouterView />
</template>
