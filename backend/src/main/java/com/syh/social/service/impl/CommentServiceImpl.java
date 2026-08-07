package com.syh.social.service.impl;

import com.syh.social.dto.CommentResponse;
import com.syh.social.dto.CreateCommentRequest;
import com.syh.social.entity.Comment;
import com.syh.social.mapper.CommentMapper;
import com.syh.social.repository.CommentRepository;
import com.syh.social.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentResponse createComment(Long postId, Long userId, String userName, CreateCommentRequest request) {
        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .content(request.getContent())
                .build();
        Long commentId = commentRepository.createComment(comment);
        comment.setCommentId(commentId);
        comment.setCreatedAt(LocalDateTime.now());
        return commentMapper.toCommentResponse(comment, userName);
    }

    @Override
    public List<CommentResponse> listComments(Long postId) {
        return commentRepository.listComments(postId);
    }
}