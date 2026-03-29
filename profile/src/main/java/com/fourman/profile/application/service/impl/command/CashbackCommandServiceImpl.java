package com.fourman.profile.application.service.impl.command;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fourman.common.enums.CashbackTransactionType;
import com.fourman.profile.application.dto.mapper.CashbackTransactionDTOMapper;
import com.fourman.profile.application.service.CashbackCommandService;
import com.fourman.profile.application.service.MembershipTierCommandService;
import com.fourman.profile.domain.CashbackTransaction;
import com.fourman.profile.domain.MembershipTier;
import com.fourman.profile.domain.Profile;
import com.fourman.profile.domain.command.ProcessCashbackCmd;
import com.fourman.profile.domain.command.UseCashbackCmd;
import com.fourman.profile.domain.repository.CashbackTransactionDomainRepository;
import com.fourman.profile.domain.repository.MembershipTierDomainRepository;
import com.fourman.profile.domain.repository.ProfileDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashbackCommandServiceImpl implements CashbackCommandService {
    private static final double PERCENTAGE_DIVISOR = 100.0;
    private final CashbackTransactionDomainRepository cashbackTransactionDomainRepository;
    private final ProfileDomainRepository profileDomainRepository;
    private final MembershipTierDomainRepository membershipTierDomainRepository;
    private final CashbackTransactionDTOMapper cashbackTransactionDTOMapper;
    private final MembershipTierCommandService membershipTierCommandService;

    @Override
    @Transactional
    public void processCashback(ProcessCashbackCmd processCashbackCmd) {
        Profile profile = profileDomainRepository.getById(processCashbackCmd.getUserId());

        MembershipTier membershipTier = membershipTierDomainRepository.getById(profile.getMembershipTierId());
        if (membershipTier == null) {
            membershipTier = membershipTierDomainRepository.getDefaultMembershipTier();
        }

        Long cashbackAmount = Math.round(
                processCashbackCmd.getOrderAmount() * membershipTier.getCashbackPercentage() / PERCENTAGE_DIVISOR);
        processCashbackCmd.setCashbackAmount(cashbackAmount);
        processCashbackCmd.setDescription("Hoàn tiền từ đơn hàng: " + processCashbackCmd.getOrderId());

        CashbackTransaction cashbackTransaction = new CashbackTransaction(processCashbackCmd);
        cashbackTransactionDomainRepository.save(cashbackTransaction);

        profile.updateUserWallet(cashbackAmount, CashbackTransactionType.EARNED);

        membershipTierCommandService
                .handleMembershipTierChange(profile.getUserWallet().getCashbackBalance())
                .ifPresent(profile::setMembershipTierId);

        profileDomainRepository.save(profile);
    }

    @Override
    @Transactional
    public void useCashback(UseCashbackCmd useCashbackCmd) {
        Profile profile = profileDomainRepository.getById(useCashbackCmd.getUserId());

        useCashbackCmd.setDescription("Sử dụng cho đơn hàng: " + useCashbackCmd.getOrderId());

        CashbackTransaction cashbackTransaction = new CashbackTransaction(useCashbackCmd);
        cashbackTransactionDomainRepository.save(cashbackTransaction);

        profile.updateUserWallet(useCashbackCmd.getAmount(), CashbackTransactionType.USED);
        profileDomainRepository.save(profile);
    }
}
