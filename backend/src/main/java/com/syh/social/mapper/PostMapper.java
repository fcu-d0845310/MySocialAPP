package com.syh.social.mapper;

import com.syh.social.dto.PostResponse;
import com.syh.social.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "commentCount", constant = "0L")
    PostResponse toPostResponse(Post post, String userName);
}