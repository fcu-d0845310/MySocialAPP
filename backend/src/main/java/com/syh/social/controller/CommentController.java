package com.syh.social.controller;

import com.syh.social.dto.CommentResponse;
import com.syh.social.dto.CreateCommentRequest;
import com.syh.social.security.UserDetail;
import com.syh.social.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@AuthenticationPrincipal UserDetail principal,
                                                           @PathVariable Long postId,
                                                           @Valid @RequestBody CreateCommentRequest request) {
        CommentResponse response = commentService.createComment(
                postId,
                principal.getUser().getUserId(),
                principal.getUser().getUserName(),
                request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> listComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.listComments(postId));
    }
}