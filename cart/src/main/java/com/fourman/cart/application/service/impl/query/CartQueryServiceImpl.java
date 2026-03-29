package com.fourman.cart.application.service.impl.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.cart.application.dto.mapper.CartDTOMapper;
import com.fourman.cart.application.service.CartQueryService;
import com.fourman.cart.domain.Cart;
import com.fourman.cart.domain.repository.CartDomainRepository;
import com.fourman.common.dto.response.CartDTO;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CartQueryServiceImpl implements CartQueryService {
    private final CartDomainRepository cartDomainRepository;
    private final CartDTOMapper cartDTOMapper;

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartDomainRepository.getAll();
        return cartDTOMapper.domainModelsToDTOs(carts);
    }
}
