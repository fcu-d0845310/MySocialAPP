<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import { listPosts } from '../api/posts'
import { listComments, createComment } from '../api/comments'
import { authState } from '../stores/auth'

const route = useRoute()
const postId = Number(route.params.id)

const post = ref(null)
const comments = ref([])
const loading = ref(true)
const loadError = ref('')

const newComment = ref('')
const posting = ref(false)
const commentError = ref('')

async function load() {
  loading.value = true
  loadError.value = ''
  try {
    // 目前後端沒有「查單一貼文」的 API，用列表過濾出這一篇
    const [posts, commentList] = await Promise.all([listPosts(), listComments(postId)])
    post.value = posts.find((p) => p.postId === postId) || null
    comments.value = commentList
  } catch (e) {
    loadError.value = e.message
  } finally {
    loading.value = false
  }
}

async function handleComment() {
  commentError.value = ''
  posting.value = true
  try {
    const comment = await createComment(postId, newComment.value)
    comments.value.push(comment)
    newComment.value = ''
  } catch (e) {
    commentError.value = e.message
  } finally {
    posting.value = false
  }
}

onMounted(load)
</script>

<template>
  <div>
    <RouterLink to="/">← 回列表</RouterLink>

    <div v-if="loading" class="empty-state">載入中...</div>
    <div v-else-if="loadError" class="error-box">{{ loadError }}</div>
    <div v-else-if="!post" class="empty-state">找不到這篇貼文（可能已被刪除）</div>

    <template v-else>
      <div class="card" style="margin-top: 16px;">
        <div class="post-meta">{{ post.userName }} · {{ new Date(post.createdAt).toLocaleString() }}</div>
        <div class="post-content">{{ post.content }}</div>
        <img v-if="post.image" :src="post.image" class="post-image" />
      </div>

      <h3>留言 ({{ comments.length }})</h3>

      <div class="card" v-if="authState.user">
        <div v-if="commentError" class="error-box">{{ commentError }}</div>
        <form @submit.prevent="handleComment">
          <div class="form-group">
            <textarea v-model="newComment" placeholder="留言..." required></textarea>
          </div>
          <button type="submit" :disabled="posting">{{ posting ? '送出中...' : '送出留言' }}</button>
        </form>
      </div>
      <div class="card muted" v-else>
        <router-link to="/login">登入</router-link>後即可留言
      </div>

      <div class="card" v-if="comments.length > 0">
        <div v-for="c in comments" :key="c.commentId" class="comment-item">
          <div class="post-meta">{{ c.userName }} · {{ new Date(c.createdAt).toLocaleString() }}</div>
          <div class="post-content">{{ c.content }}</div>
        </div>
      </div>
      <div v-else class="empty-state">還沒有留言，來搶頭香吧！</div>
    </template>
  </div>
</template>
