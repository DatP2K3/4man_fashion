package com.evo.profile.application.service;

import java.util.List;

import com.evo.common.dto.response.MembershipTierDTO;

public interface MembershipTierQueryService {
    List<MembershipTierDTO> findAll();
}
