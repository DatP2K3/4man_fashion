package com.evotek.iam.application.service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.evo.common.UserAuthority;
import com.evotek.iam.application.dto.request.SearchUserRequest;
import com.evotek.iam.application.dto.response.UserDTO;
import com.evotek.iam.domain.UserRole;
import com.evotek.iam.infrastructure.persistence.entity.UserRoleEntity;

public interface UserQueryService {
    UserDTO getMyUserInfo();

    Long totalUsers(SearchUserRequest request);

    List<UserDTO> search(SearchUserRequest searchUserRequest);

    void exportUserListToExcel(SearchUserRequest searchUserRequest);

    UserAuthority getUserAuthority(String username);

    UserAuthority getClientAuthority(String clientId);
}
