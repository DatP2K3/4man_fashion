package com.fourman.product.domain.repository;

import java.util.List;
import java.util.UUID;

import com.fourman.common.enums.DiscountStatus;
import com.fourman.common.repository.DomainRepository;
import com.fourman.product.domain.Discount;

public interface DiscountDomainRepository extends DomainRepository<Discount, UUID> {
    List<Discount> getAll();

    List<Discount> getAllNotIn(List<DiscountStatus> discountStatuses);
}
