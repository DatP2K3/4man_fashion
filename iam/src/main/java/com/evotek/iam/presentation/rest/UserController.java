package com.evotek.iam.presentation.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.evo.common.UserAuthority;
import com.evo.common.dto.response.ApiResponses;
import com.evotek.iam.application.dto.request.CreateOrUpdateUserRequest;
import com.evotek.iam.application.dto.request.SearchUserRequest;
import com.evotek.iam.application.dto.response.UserDTO;
import com.evotek.iam.application.service.UserCommandService;
import com.evotek.iam.application.service.UserQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @PreAuthorize("hasPermission(null, 'user.admin')")
    @GetMapping("/users/authorities-by-username/{username}")
    public ApiResponses<UserAuthority> getUserAuthority(@PathVariable String username) {
        UserAuthority userAuthority = userQueryService.getUserAuthority(username);
        return ApiResponses.<UserAuthority>builder()
                .data(userAuthority)
                .success(true)
                .code(200)
                .message("Get client authority successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @Operation(
            summary = "Lấy thông tin người dùng",
            description = "API này sẽ trả về thông tin người dùng trong hệ thống.",
            responses = {@ApiResponse(responseCode = "200", description = "Thông tin người dùng")})
    //    @PreAuthorize("hasPermission(null, 'user.read')")
    @GetMapping("/users/my-info")
    @PreAuthorize("hasRole('USER')")
    public ApiResponses<UserDTO> getMyInfo() {
        UserDTO userDTO = userQueryService.getMyUserInfo();
        return ApiResponses.<UserDTO>builder()
                .data(userDTO)
                .success(true)
                .code(200)
                .message("Get my info successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @Operation(
            summary = "Cập nhật thông tin người dùng",
            description = "API này sẽ trả cập nhật thông tin người dùng trong hệ thống và trả về thông tin.",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Thông tin của người dùng cần cập nhật",
                            required = true,
                            content = @Content(schema = @Schema(implementation = CreateOrUpdateUserRequest.class))),
            responses = {@ApiResponse(responseCode = "200", description = "Thông tin người dùng sau khi cập nhật")})
    @PreAuthorize("hasPermission(null, 'user.update')")
    @PutMapping("/users")
    public ApiResponses<UserDTO> updateUser(@RequestBody CreateOrUpdateUserRequest updateUserRequest) {
        UserDTO userDTO = userCommandService.updateMyUser(updateUserRequest);
        return ApiResponses.<UserDTO>builder()
                .data(userDTO)
                .success(true)
                .code(200)
                .message("Update user successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @Operation(
            summary = "Khóa/Mở khóa người dùng",
            description = "API này sẽ khoá hoặc mở khóa một người dùng trong hệ thống.",
            responses = {@ApiResponse(responseCode = "200", description = "Khóa/Mở khóa người dùng thành công")})
    @PreAuthorize("hasPermission(null, 'user.delete')")
    @PatchMapping("/users/lock")
    public ApiResponses<Void> lockUser(
            @Parameter(description = "ID của người dùng cần cập nhật trạng thái", example = "12345") @RequestParam
                    String username,
            @Parameter(description = "Trạng thái của người dùng: `true` để khoá, `false` để mở khoá", example = "true")
                    @RequestParam
                    boolean enabled) {
        userCommandService.lockUser(username, enabled);
        return ApiResponses.<Void>builder()
                .success(true)
                .code(200)
                .message("Lock/Unlock user successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/users/export")
    public ApiResponses<Void> exportUserListToExcel(@RequestBody SearchUserRequest searchUserRequest) {
        userQueryService.exportUserListToExcel(searchUserRequest);
        return ApiResponses.<Void>builder()
                .success(true)
                .code(200)
                .message("Export user list to excel successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
