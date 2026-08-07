package com.syh.social.mapper;

import com.syh.social.dto.RegisterResponse;
import com.syh.social.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    RegisterResponse toRegisterResponse(User user);
}