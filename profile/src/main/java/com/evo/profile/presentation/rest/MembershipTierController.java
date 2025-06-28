package com.evo.profile.presentation.rest;

import com.evo.common.dto.response.Response;
import com.evo.common.dto.response.MembershipTierDTO;
import com.evo.profile.application.dto.request.CreateOrUpdateMembershipTierRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
