import request from './http'

export function listPosts() {
  return request('/posts', { method: 'GET' })
}

export function createPost(payload) {
  // payload: { content, image? }
  return request('/posts', { method: 'POST', body: payload, auth: true })
}

export function updatePost(postId, payload) {
  return request(`/posts/${postId}`, { method: 'PUT', body: payload, auth: true })
}

export function deletePost(postId) {
  return request(`/posts/${postId}`, { method: 'DELETE', auth: true })
}