package com.syh.social.repository.impl;

import com.syh.social.dto.CommentResponse;
import com.syh.social.entity.Comment;
import com.syh.social.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final JdbcClient jdbcClient;

    @Override
    public Long createComment(Comment comment) {
        return jdbcClient
                .sql("SELECT sp_create_comment(:postId, :userId, :content)")
                .param("postId", comment.getPostId())
                .param("userId", comment.getUserId())
                .param("content", comment.getContent())
                .query(Long.class)
                .single();
    }

    @Override
    public List<CommentResponse> listComments(Long postId) {
        return jdbcClient
                .sql("SELECT * FROM sp_list_comments(:postId)")
                .param("postId", postId)
                .query(CommentResponse.class)
                .list();
    }
}