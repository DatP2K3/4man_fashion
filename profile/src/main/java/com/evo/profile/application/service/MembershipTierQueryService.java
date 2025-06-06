package com.evo.profile.application.service;

import com.evo.common.dto.response.MembershipTierDTO;

import java.util.List;

public interface MembershipTierQueryService {
    List<MembershipTierDTO> findAll();
}
