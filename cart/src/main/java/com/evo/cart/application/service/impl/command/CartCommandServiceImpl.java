package com.evo.cart.application.service.impl.command;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.evo.cart.application.dto.mapper.CartDTOMapper;
import com.evo.cart.application.dto.request.UpdateCartRequest;
import com.evo.cart.application.dto.response.CartDTO;
import com.evo.cart.application.mapper.CommandMapper;
import com.evo.cart.application.service.CartCommandService;
import com.evo.cart.domain.Cart;
import com.evo.cart.domain.command.UpdateCartCmd;
import com.evo.cart.domain.repository.CartDomainRepository;
import com.evo.cart.infrastructure.support.exception.AppErrorCode;
import com.evo.cart.infrastructure.support.exception.AppException;

import lombok.RequiredArgsConstructor;

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
            throw new AppException(AppErrorCode.CART_NOT_FOUND);
        }
        cart.update(updateCartCmd);
        cart = cartDomainRepository.save(cart);
        return cartDTOMapper.domainModelToDTO(cart);
    }
}
