package com.fourman.cart.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.cart.application.dto.request.CreateCartItemRequest;
import com.fourman.cart.application.dto.request.UpdateCartRequest;
import com.fourman.cart.domain.command.CreateCartItemCmd;
import com.fourman.cart.domain.command.UpdateCartCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    UpdateCartCmd from(UpdateCartRequest request);

    CreateCartItemCmd from(CreateCartItemRequest request);
}
