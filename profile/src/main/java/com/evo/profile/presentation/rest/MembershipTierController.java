package com.evo.profile.presentation.rest;

import com.evo.profile.application.service.MembershipTierQueryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.MembershipTierDTO;
import com.evo.profile.application.dto.request.CreateOrUpdateMembershipTierRequest;
import com.evo.profile.application.service.MembershipTierCommandService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MembershipTierController {
    private final MembershipTierCommandService membershipTierCommandService;
    private final MembershipTierQueryService membershipTierQueryService;

    @PreAuthorize("hasRole('AMIN')")
    @PostMapping("/membership-tiers")
    ApiResponses<MembershipTierDTO> createMembershipTier(
            @RequestBody CreateOrUpdateMembershipTierRequest createMembershipTierRequest) {
        MembershipTierDTO membershipTierDTO = membershipTierCommandService.create(createMembershipTierRequest);
        return ApiResponses.<MembershipTierDTO>builder()
                .data(membershipTierDTO)
                .success(true)
                .code(201)
                .message("MembershipTier created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/membership-tiers")
    ApiResponses<MembershipTierDTO> updateMembershipTier(
            @RequestBody CreateOrUpdateMembershipTierRequest updateMembershipTierRequest) {
        MembershipTierDTO membershipTierDTO = membershipTierCommandService.update(updateMembershipTierRequest);
        return ApiResponses.<MembershipTierDTO>builder()
                .data(membershipTierDTO)
                .success(true)
                .code(200)
                .message("MembershipTier updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/membership-tiers")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponses<List<MembershipTierDTO>> getAllMembershipTiers() {
        List<MembershipTierDTO> membershipTiers = membershipTierQueryService.findAll();
        return ApiResponses.<List<MembershipTierDTO>>builder()
                .data(membershipTiers)
                .success(true)
                .code(200)
                .message("Membership Tiers retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PutMapping("/membership-tiers/{id}/toggle-visibility")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponses<Void> toggleMembershipTierVisibility(@PathVariable String id) {
        membershipTierCommandService.toggleVisibility(id);
        return ApiResponses.<Void>builder()
                .success(true)
                .code(200)
                .message("Membership Tier visibility toggled successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
