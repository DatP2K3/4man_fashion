package com.fourman.profile.application.service.impl.query;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.profile.application.dto.mapper.CashbackTransactionDTOMapper;
import com.fourman.profile.application.dto.response.CashbackTransactionDTO;
import com.fourman.profile.application.service.CashbackQueryService;
import com.fourman.profile.domain.CashbackTransaction;
import com.fourman.profile.domain.repository.CashbackTransactionDomainRepository;
import com.fourman.profile.domain.repository.ProfileDomainRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
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
