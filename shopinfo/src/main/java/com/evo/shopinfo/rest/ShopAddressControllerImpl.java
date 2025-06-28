package com.evo.shopinfo.rest;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.Response;
import com.evo.common.dto.response.ShopAddressDTO;
import com.evo.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.evo.shopinfo.application.service.ShopAddressCommandService;
import com.evo.shopinfo.application.service.ShopAddressQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShopAddressControllerImpl implements ShopAddressController {
    private final ShopAddressCommandService shopAddressCommandService;
    private final ShopAddressQueryService shopAddressQueryService;

    @Override
    public Response<ShopAddressDTO> updateShopAddress(@RequestBody CreateOrUpdateShopAddressRequest request) {
        return Response.of(this.shopAddressCommandService.update(request));
    }

    @Override
    public Response<List<ShopAddressDTO>> getAllShopAddresses() {
        return Response.of(this.shopAddressQueryService.getAllShopAddresses());
    }
}
