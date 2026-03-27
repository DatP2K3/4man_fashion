package com.fourman.profile.presentation.rest;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fourman.common.dto.response.MembershipTierDTO;
import com.fourman.common.dto.response.Response;
import com.fourman.profile.application.dto.request.CreateOrUpdateMembershipTierRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Membership Tier API")
@RequestMapping("/api")
@Validated
public interface MembershipTierController {

    @Operation(summary = "Create membership tier")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/membership-tiers")
    Response<MembershipTierDTO> createMembershipTier(
            @RequestBody CreateOrUpdateMembershipTierRequest createMembershipTierRequest);

    @Operation(summary = "Update membership tier")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/membership-tiers")
    Response<MembershipTierDTO> updateMembershipTier(
            @RequestBody CreateOrUpdateMembershipTierRequest updateMembershipTierRequest);

    @Operation(summary = "Get all membership tiers")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/membership-tiers")
    Response<List<MembershipTierDTO>> getAllMembershipTiers();

    @Operation(summary = "Toggle membership tier visibility")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/membership-tiers/{id}/toggle-visibility")
    Response<Void> toggleMembershipTierVisibility(@PathVariable String id);
}
