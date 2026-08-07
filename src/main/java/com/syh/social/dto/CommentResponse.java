package com.syh.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 對應 sp_list_comments() 回傳的欄位(留言、作者)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private Long commentId;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private Long userId;
    private String userName;
}