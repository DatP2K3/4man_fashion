package com.evo.profile.application.service.impl.query;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.evo.profile.application.dto.mapper.CashbackTransactionDTOMapper;
import com.evo.profile.application.dto.response.CashbackTransactionDTO;
import com.evo.profile.application.service.CashbackQueryService;
import com.evo.profile.domain.CashbackTransaction;
import com.evo.profile.domain.Profile;
import com.evo.profile.domain.UserWallet;
import com.evo.profile.domain.repository.CashbackTransactionDomainRepository;
import com.evo.profile.domain.repository.ProfileDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashbackQueryServiceImpl implements CashbackQueryService {
    private final CashbackTransactionDomainRepository cashbackTransactionDomainRepository;
    private final ProfileDomainRepository profileDomainRepository;
    private final CashbackTransactionDTOMapper cashbackTransactionDTOMapper;

    @Override
    public List<CashbackTransactionDTO> getUserCashbackHistory() {
        var context = SecurityContextHolder.getContext();
        UUID userId = UUID.fromString(context.getAuthentication().getName());
        List<CashbackTransaction> transactions = cashbackTransactionDomainRepository.findByUserId(userId);
        return cashbackTransactionDTOMapper.domainModelsToDTOs(transactions);
    }
}
