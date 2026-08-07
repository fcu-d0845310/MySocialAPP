package com.syh.social.service;

import com.syh.social.dto.CommentResponse;
import com.syh.social.dto.CreateCommentRequest;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(Long postId, Long userId, String userName, CreateCommentRequest request);
    List<CommentResponse> listComments(Long postId);
}