package com.fourman.profile.application.service;

import java.util.List;

import com.fourman.common.dto.response.MembershipTierDTO;

public interface MembershipTierQueryService {
    List<MembershipTierDTO> findAll();
}
