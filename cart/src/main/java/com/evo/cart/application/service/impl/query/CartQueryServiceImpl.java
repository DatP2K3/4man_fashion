package com.evo.cart.application.service.impl.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.evo.cart.application.dto.mapper.CartDTOMapper;
import com.evo.cart.application.service.CartQueryService;
import com.evo.cart.domain.Cart;
import com.evo.cart.domain.repository.CartDomainRepository;
import com.evo.common.dto.response.CartDTO;

import lombok.RequiredArgsConstructor;

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
