import request from './http'

export function listComments(postId) {
  return request(`/posts/${postId}/comments`, { method: 'GET' })
}

export function createComment(postId, content) {
  return request(`/posts/${postId}/comments`, {
    method: 'POST',
    body: { content },
    auth: true,
  })
}