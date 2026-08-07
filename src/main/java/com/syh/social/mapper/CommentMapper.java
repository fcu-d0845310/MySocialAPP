package com.syh.social.mapper;

import com.syh.social.dto.CommentResponse;
import com.syh.social.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    @Mapping(target = "userName", source = "userName")
    CommentResponse toCommentResponse(Comment comment, String userName);
}