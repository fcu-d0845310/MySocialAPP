package com.syh.social.service;

import com.syh.social.dto.CreatePostRequest;
import com.syh.social.dto.PostResponse;
import com.syh.social.dto.UpdatePostRequest;

import java.util.List;

public interface PostService {
    PostResponse createPost(Long userId, String userName, CreatePostRequest request);
    List<PostResponse> listPosts();
    void updatePost(Long postId, Long userId, UpdatePostRequest request);
    void deletePost(Long postId, Long userId);
}
