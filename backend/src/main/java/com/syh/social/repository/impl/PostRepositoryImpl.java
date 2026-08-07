package com.syh.social.repository.impl;

import com.syh.social.dto.PostResponse;
import com.syh.social.entity.Post;
import com.syh.social.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final JdbcClient jdbcClient;

    @Override
    public Long createPost(Post post) {
        return jdbcClient
                .sql("SELECT sp_create_post(:userId, :content, :image)")
                .param("userId", post.getUserId())
                .param("content", post.getContent())
                .param("image", post.getImage())
                .query(Long.class)
                .single();
    }

    @Override
    public List<PostResponse> listPosts() {
        return jdbcClient
                .sql("SELECT * FROM sp_list_posts()")
                .query(PostResponse.class)
                .list();
    }

    @Override
    public int updatePost(Long postId, Long userId, String content, String image) {
        return jdbcClient
                .sql("SELECT sp_update_post(:postId, :userId, :content, :image)")
                .param("postId", postId)
                .param("userId", userId)
                .param("content", content)
                .param("image", image)
                .query(Integer.class)
                .single();
    }

    @Override
    public int deletePost(Long postId, Long userId) {
        return jdbcClient
                .sql("SELECT sp_delete_post_with_comments(:postId, :userId)")
                .param("postId", postId)
                .param("userId", userId)
                .query(Integer.class)
                .single();
    }
}
