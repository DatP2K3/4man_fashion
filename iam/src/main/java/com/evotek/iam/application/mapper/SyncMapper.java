package com.evotek.iam.application.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.evo.common.dto.request.SyncProductRequest;
import com.evotek.iam.application.dto.request.CreateOrUpdateUserRequest;
import com.evotek.iam.domain.User;
import com.evotek.iam.domain.UserRole;

@Mapper(componentModel = "spring")
public interface SyncMapper {
    @Mapping(source = "userRoles", target = "roleIds", qualifiedByName = "userRolesToRoleIds")
    SyncProductRequest from(User user);

    CreateOrUpdateUserRequest from(SyncProductRequest request);

    @Named("userRolesToRoleIds")
    default List<UUID> userRolesToRoleIds(List<UserRole> userRoles) {
        if (userRoles == null) {
            return null;
        }
        return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }
}
