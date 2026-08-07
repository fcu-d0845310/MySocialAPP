package com.syh.social.repository;

import com.syh.social.dto.CommentResponse;
import com.syh.social.entity.Comment;

import java.util.List;

public interface CommentRepository {
    Long createComment(Comment comment);
    List<CommentResponse> listComments(Long postId);
}