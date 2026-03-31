package com.fourman.publicinfo.shopinfo.presentation.rest;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.fourman.common.dto.response.Response;
import com.fourman.common.dto.response.ShopAddressDTO;
import com.fourman.publicinfo.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.fourman.publicinfo.shopinfo.application.service.ShopAddressCommandService;
import com.fourman.publicinfo.shopinfo.application.service.ShopAddressQueryService;

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
