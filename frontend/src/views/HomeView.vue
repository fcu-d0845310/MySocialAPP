<script setup>
import { ref, onMounted } from 'vue'
import { listPosts, createPost, updatePost, deletePost } from '../api/posts'
import { authState, isOwner } from '../stores/auth'

const posts = ref([])
const loading = ref(true)
const loadError = ref('')

const newContent = ref('')
const newImage = ref('')
const posting = ref(false)
const postError = ref('')

const editingId = ref(null)
const editContent = ref('')
const editImage = ref('')
const editError = ref('')

async function loadPosts() {
  loading.value = true
  loadError.value = ''
  try {
    posts.value = await listPosts()
  } catch (e) {
    loadError.value = e.message
  } finally {
    loading.value = false
  }
}

async function handleCreate() {
  postError.value = ''
  posting.value = true
  try {
    const post = await createPost({ content: newContent.value, image: newImage.value || null })
    posts.value.unshift(post)
    newContent.value = ''
    newImage.value = ''
  } catch (e) {
    postError.value = e.message
  } finally {
    posting.value = false
  }
}

function startEdit(post) {
  editingId.value = post.postId
  editContent.value = post.content
  editImage.value = post.image || ''
  editError.value = ''
}

function cancelEdit() {
  editingId.value = null
}

async function saveEdit(post) {
  editError.value = ''
  try {
    await updatePost(post.postId, { content: editContent.value, image: editImage.value || null })
    post.content = editContent.value
    post.image = editImage.value || null
    editingId.value = null
  } catch (e) {
    editError.value = e.message
  }
}

async function handleDelete(post) {
  if (!confirm('確定要刪除這篇貼文嗎？底下的留言也會一起被刪除。')) return
  try {
    await deletePost(post.postId)
    posts.value = posts.value.filter((p) => p.postId !== post.postId)
  } catch (e) {
    alert(e.message)
  }
}

onMounted(loadPosts)
</script>

<template>
  <div>
    <div class="card" v-if="authState.user">
      <h3 style="margin-top: 0;">分享點什麼吧</h3>
      <div v-if="postError" class="error-box">{{ postError }}</div>
      <form @submit.prevent="handleCreate">
        <div class="form-group">
          <textarea v-model="newContent" placeholder="想說些什麼..." required></textarea>
        </div>
        <div class="form-group">
          <input v-model="newImage" type="text" placeholder="圖片網址（選填）" />
        </div>
        <button type="submit" :disabled="posting">{{ posting ? '發布中...' : '發布' }}</button>
      </form>
    </div>
    <div class="card muted" v-else>
      <router-link to="/login">登入</router-link>後即可發文
    </div>

    <div v-if="loading" class="empty-state">載入中...</div>
    <div v-else-if="loadError" class="error-box">{{ loadError }}</div>
    <div v-else-if="posts.length === 0" class="empty-state">還沒有任何貼文，當第一個發文的人吧！</div>

    <div v-else v-for="post in posts" :key="post.postId" class="card">
      <template v-if="editingId === post.postId">
        <div v-if="editError" class="error-box">{{ editError }}</div>
        <div class="form-group">
          <textarea v-model="editContent"></textarea>
        </div>
        <div class="form-group">
          <input v-model="editImage" type="text" placeholder="圖片網址（選填）" />
        </div>
        <div class="btn-row">
          <button @click="saveEdit(post)">儲存</button>
          <button class="secondary" @click="cancelEdit">取消</button>
        </div>
      </template>
      <template v-else>
        <div class="post-meta">{{ post.userName }} · {{ new Date(post.createdAt).toLocaleString() }}</div>
        <div class="post-content">{{ post.content }}</div>
        <img v-if="post.image" :src="post.image" class="post-image" />
        <div class="btn-row">
          <router-link :to="`/posts/${post.postId}`">
            <button class="secondary">留言 ({{ post.commentCount }})</button>
          </router-link>
          <template v-if="isOwner(post.userId)">
            <button class="secondary" @click="startEdit(post)">編輯</button>
            <button class="danger" @click="handleDelete(post)">刪除</button>
          </template>
        </div>
      </template>
    </div>
  </div>
</template>
