package com.syh.social.controller;

import com.syh.social.dto.CreatePostRequest;
import com.syh.social.dto.PostResponse;
import com.syh.social.dto.UpdatePostRequest;
import com.syh.social.security.UserDetail;
import com.syh.social.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@AuthenticationPrincipal UserDetail principal,
                                         @Valid @RequestBody CreatePostRequest request) {
        PostResponse response = postService.createPost(
                principal.getUser().getUserId(),
                principal.getUser().getUserName(),
                request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> listPosts() {
        return ResponseEntity.ok(postService.listPosts());
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@AuthenticationPrincipal UserDetail principal,
                                         @PathVariable Long postId,
                                         @Valid @RequestBody UpdatePostRequest request) {
        postService.updatePost(postId, principal.getUser().getUserId(), request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal UserDetail principal,
                                         @PathVariable Long postId) {
        postService.deletePost(postId, principal.getUser().getUserId());
        return ResponseEntity.noContent().build();
    }
}
