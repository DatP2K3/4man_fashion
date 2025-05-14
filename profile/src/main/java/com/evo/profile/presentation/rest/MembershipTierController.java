package com.evo.profile.presentation.rest;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.MembershipTierDTO;
import com.evo.profile.application.dto.request.CreateOrUpdateMembershipTierRequest;
import com.evo.profile.application.service.MembershipTierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MembershipTierController {
    private final MembershipTierService membershipTierService;

    @PostMapping("/membership-tiers")
    ApiResponses<MembershipTierDTO> createMembershipTier(
            @RequestBody CreateOrUpdateMembershipTierRequest createMembershipTierRequest) {
        MembershipTierDTO membershipTierDTO = membershipTierService.create(createMembershipTierRequest);
        return ApiResponses.<MembershipTierDTO>builder()
                .data(membershipTierDTO)
                .success(true)
                .code(201)
                .message("MembershipTier created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PutMapping("/membership-tiers")
    ApiResponses<MembershipTierDTO> updateMembershipTier(
            @RequestBody CreateOrUpdateMembershipTierRequest updateMembershipTierRequest) {
        MembershipTierDTO membershipTierDTO = membershipTierService.update(updateMembershipTierRequest);
        return ApiResponses.<MembershipTierDTO>builder()
                .data(membershipTierDTO)
                .success(true)
                .code(200)
                .message("MembershipTier updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
