package com.fourman.cart.application.service.impl.command;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fourman.cart.application.dto.mapper.CartDTOMapper;
import com.fourman.cart.application.dto.request.UpdateCartRequest;
import com.fourman.cart.application.mapper.CommandMapper;
import com.fourman.cart.application.service.CartCommandService;
import com.fourman.cart.domain.Cart;
import com.fourman.cart.domain.command.UpdateCartCmd;
import com.fourman.cart.domain.exception.NotFoundError;
import com.fourman.cart.domain.repository.CartDomainRepository;
import com.fourman.common.dto.response.CartDTO;
import com.fourman.common.exception.ResponseException;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class CartCommandServiceImpl implements CartCommandService {
    private final CartDomainRepository cartDomainRepository;
    private final CommandMapper commandMapper;
    private final CartDTOMapper cartDTOMapper;

    @Override
    public CartDTO getOrInitCart() {
        var context = SecurityContextHolder.getContext();
        UUID userId = UUID.fromString(context.getAuthentication().getName());

        Cart cart = cartDomainRepository.getByUserIdOrNull(userId);
        if (cart != null) {
            return cartDTOMapper.domainModelToDTO(cart);
        }
        UpdateCartCmd updateCartCmd = UpdateCartCmd.builder().userId(userId).build();
        cart = new Cart(updateCartCmd);
        cart = cartDomainRepository.save(cart);
        return cartDTOMapper.domainModelToDTO(cart);
    }

    @Override
    public CartDTO updateCart(UpdateCartRequest request) {
        UpdateCartCmd updateCartCmd = commandMapper.from(request);
        Cart cart = cartDomainRepository.getById(updateCartCmd.getId());
        if (cart == null) {
            throw new ResponseException(NotFoundError.CART_NOT_FOUND);
        }
        cart.update(updateCartCmd);
        cart = cartDomainRepository.save(cart);
        return cartDTOMapper.domainModelToDTO(cart);
    }

    @Override
    public void emptyCart(UUID cartId) {
        Cart cart = cartDomainRepository.getById(cartId);
        cart.emptyCart();
        cartDomainRepository.save(cart);
    }
}
