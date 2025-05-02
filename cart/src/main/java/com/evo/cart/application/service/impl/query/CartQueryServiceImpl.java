package com.evo.cart.application.service.impl.query;

import com.evo.cart.application.dto.mapper.CartDTOMapper;
import com.evo.cart.application.dto.response.CartDTO;
import com.evo.cart.application.service.CartQueryService;
import com.evo.cart.domain.Cart;
import com.evo.cart.domain.repository.CartDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
