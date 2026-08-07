package com.syh.social.service.impl;

import com.syh.social.dto.CreatePostRequest;
import com.syh.social.dto.PostResponse;
import com.syh.social.dto.UpdatePostRequest;
import com.syh.social.entity.Post;
import com.syh.social.mapper.PostMapper;
import com.syh.social.repository.PostRepository;
import com.syh.social.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    @Transactional
    public PostResponse createPost(Long userId, String userName, CreatePostRequest request){
        Post post = Post.builder()
                .userId(userId)
                .content(request.getContent())
                .image(request.getImage())
                .build();
        Long postId = postRepository.createPost(post);
        post.setPostId(postId);
        post.setCreatedAt(LocalDateTime.now());
        return postMapper.toPostResponse(post, userName);
    }

    @Override
    public List<PostResponse> listPosts() {
        return postRepository.listPosts();
    }

    @Override
    @Transactional
    public void updatePost(Long postId, Long userId, UpdatePostRequest request){
        int count = postRepository.updatePost(postId, userId, request.getContent(), request.getImage());

        if (count == 0) {
            throw new IllegalArgumentException("Cannot find the post or you don't have authorize");
        }
    }

    @Override
    @Transactional
    public void deletePost(Long postId, Long userId){
        int count = postRepository.deletePost(postId, userId);
        if(count == 0){
            throw new IllegalArgumentException("Cannot find the post or you don't have authorize");
        }
    }
}
