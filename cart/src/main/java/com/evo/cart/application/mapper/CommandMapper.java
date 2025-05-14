package com.evo.cart.application.mapper;

import org.mapstruct.Mapper;

import com.evo.cart.application.dto.request.CreateCartItemRequest;
import com.evo.cart.application.dto.request.UpdateCartRequest;
import com.evo.cart.domain.command.CreateCartItemCmd;
import com.evo.cart.domain.command.UpdateCartCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    UpdateCartCmd from(UpdateCartRequest request);

    CreateCartItemCmd from(CreateCartItemRequest request);
}
