package com.syh.social.repository;

import com.syh.social.dto.PostResponse;
import com.syh.social.entity.Post;

import java.util.List;

public interface PostRepository {
    Long createPost(Post post);
    List<PostResponse> listPosts();
    int updatePost(Long postId, Long userId, String content, String image);
    int deletePost(Long postId, Long userId);
}
