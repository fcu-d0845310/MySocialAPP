package com.syh.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 對應 sp_list_posts() 回傳的欄位(貼文 + 作者 + 留言數)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private Long postId;
    private String content;
    private String image;
    private LocalDateTime createdAt;
    private Long userId;
    private String userName;
    private Long commentCount;
}
