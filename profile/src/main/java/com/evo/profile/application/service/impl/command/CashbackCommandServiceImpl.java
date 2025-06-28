package com.evo.profile.application.service.impl.command;

import java.util.UUID;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.evo.common.enums.CashbackTransactionType;
import com.evo.profile.application.dto.mapper.CashbackTransactionDTOMapper;
import com.evo.profile.application.service.CashbackCommandService;
import com.evo.profile.application.service.MembershipTierCommandService;
import com.evo.profile.domain.CashbackTransaction;
import com.evo.profile.domain.MembershipTier;
import com.evo.profile.domain.Profile;
import com.evo.profile.domain.command.ProcessCashbackCmd;
import com.evo.profile.domain.command.UseCashbackCmd;
import com.evo.profile.domain.repository.CashbackTransactionDomainRepository;
import com.evo.profile.domain.repository.MembershipTierDomainRepository;
import com.evo.profile.domain.repository.ProfileDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashbackCommandServiceImpl implements CashbackCommandService {
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

        Long cashbackAmount =
                Math.round(processCashbackCmd.getOrderAmount() * membershipTier.getCashbackPercentage() / 100);
        processCashbackCmd.setCashbackAmount(cashbackAmount);
        processCashbackCmd.setDescription("Hoàn tiền từ đơn hàng: " + processCashbackCmd.getOrderId());

        CashbackTransaction cashbackTransaction = new CashbackTransaction(processCashbackCmd);
        cashbackTransactionDomainRepository.save(cashbackTransaction);

        profile.updateUserWallet(cashbackAmount, CashbackTransactionType.EARNED);

        UUID membershipTierId = membershipTierCommandService.handleMembershipTierChange(
                profile.getUserWallet().getCashbackBalance());
        if (membershipTierId != null) {
            profile.setMembershipTierId(membershipTierId);
        }

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
