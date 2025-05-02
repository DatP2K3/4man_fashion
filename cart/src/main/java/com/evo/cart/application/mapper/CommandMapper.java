package com.evo.cart.application.mapper;

import com.evo.cart.application.dto.request.CreateCartItemRequest;
import com.evo.cart.application.dto.request.UpdateCartRequest;
import com.evo.cart.domain.command.UpdateCartCmd;
import com.evo.cart.domain.command.CreateCartItemCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    UpdateCartCmd from (UpdateCartRequest request);
    CreateCartItemCmd from (CreateCartItemRequest request);
}
