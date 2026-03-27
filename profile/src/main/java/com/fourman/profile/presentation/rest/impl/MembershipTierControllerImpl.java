package com.fourman.profile.presentation.rest.impl;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.fourman.common.dto.response.MembershipTierDTO;
import com.fourman.common.dto.response.Response;
import com.fourman.profile.application.dto.request.CreateOrUpdateMembershipTierRequest;
import com.fourman.profile.application.service.MembershipTierCommandService;
import com.fourman.profile.application.service.MembershipTierQueryService;
import com.fourman.profile.presentation.rest.MembershipTierController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MembershipTierControllerImpl implements MembershipTierController {
    private final MembershipTierCommandService membershipTierCommandService;
    private final MembershipTierQueryService membershipTierQueryService;

    @Override
    public Response<MembershipTierDTO> createMembershipTier(
            @RequestBody CreateOrUpdateMembershipTierRequest createMembershipTierRequest) {
        return Response.of(this.membershipTierCommandService.create(createMembershipTierRequest));
    }

    @Override
    public Response<MembershipTierDTO> updateMembershipTier(
            @RequestBody CreateOrUpdateMembershipTierRequest updateMembershipTierRequest) {
        return Response.of(this.membershipTierCommandService.update(updateMembershipTierRequest));
    }

    @Override
    public Response<List<MembershipTierDTO>> getAllMembershipTiers() {
        return Response.of(this.membershipTierQueryService.findAll());
    }

    @Override
    public Response<Void> toggleMembershipTierVisibility(@PathVariable String id) {
        this.membershipTierCommandService.toggleVisibility(id);
        return Response.ok();
    }
}
