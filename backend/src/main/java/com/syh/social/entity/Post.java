                                                    package com.syh.social.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Post {
    private Long postId;
    private Long userId;
    private String content;
    private String image;
    private LocalDateTime createdAt;
}