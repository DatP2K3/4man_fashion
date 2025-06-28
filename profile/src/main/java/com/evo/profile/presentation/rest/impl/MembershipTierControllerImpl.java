package com.evo.profile.presentation.rest;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.MembershipTierDTO;
import com.evo.profile.application.dto.request.CreateOrUpdateMembershipTierRequest;
import com.evo.profile.application.service.MembershipTierCommandService;
import com.evo.profile.application.service.MembershipTierQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MembershipTierControllerImpl implements MembershipTierController {
    private final MembershipTierCommandService membershipTierCommandService;
    private final MembershipTierQueryService membershipTierQueryService;

    @Override
    public ApiResponses<MembershipTierDTO> createMembershipTier(
            @RequestBody CreateOrUpdateMembershipTierRequest createMembershipTierRequest) {
        return ApiResponses.of(this.membershipTierCommandService.create(createMembershipTierRequest));
    }

    @Override
    public ApiResponses<MembershipTierDTO> updateMembershipTier(
            @RequestBody CreateOrUpdateMembershipTierRequest updateMembershipTierRequest) {
        return ApiResponses.of(this.membershipTierCommandService.update(updateMembershipTierRequest));
    }

    @Override
    public ApiResponses<List<MembershipTierDTO>> getAllMembershipTiers() {
        return ApiResponses.of(this.membershipTierQueryService.findAll());
    }

    @Override
    public ApiResponses<Void> toggleMembershipTierVisibility(@PathVariable String id) {
        this.membershipTierCommandService.toggleVisibility(id);
        return ApiResponses.ok();
    }
}
