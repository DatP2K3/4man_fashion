package com.evo.shopinfo.application.config;

import com.evo.common.enums.ShopAddressType;
import com.evo.shopinfo.application.dto.request.CreateOrUpdateShopAddressRequest;
import com.evo.common.dto.response.ShopAddressDTO;
import com.evo.shopinfo.application.service.ShopAddressCommandService;
import com.evo.shopinfo.application.service.ShopAddressQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class shopInfoInitConfig {
    private final ShopAddressCommandService shopAddressCommandService;
    private final ShopAddressQueryService shopAddressQueryService;

    @Bean
    ApplicationRunner applicationRunner(ShopAddressCommandService shopAddressCommandService) {
        log.info("Initializing application.....");
        return args -> {
            List<ShopAddressDTO> shopAddressDTOS = shopAddressQueryService.getAllShopAddresses();
            if (shopAddressDTOS != null && shopAddressDTOS.size() == 2) {
                log.info("Shop address data already exists in the database.");
        } else {
                CreateOrUpdateShopAddressRequest createShopAddressSendCmd = CreateOrUpdateShopAddressRequest.builder()
                        .shopName("4Man Fashion")
                        .phoneNumber("0862269885")
                        .addressLine1("Xưởng cho thuê cốt pha")
                        .addressLine2("Thôn Táo 3")
                        .city("Hà Nội")
                        .ward("Phường Tây Mỗ")
                        .district("Quận Nam Từ Liêm")
                        .districtId("3440")
                        .wardCode("13008")
                        .type(ShopAddressType.SEND_ADDRESS)
                        .build();

                CreateOrUpdateShopAddressRequest createShopAddressReturnRequest = CreateOrUpdateShopAddressRequest.builder()
                        .shopName("4Man Fashion")
                        .phoneNumber("0862269885")
                        .addressLine1("Xưởng cho thuê cốt pha")
                        .addressLine2("Thôn Táo 3")
                        .city("Hà Nội")
                        .ward("Phường Tây Mỗ")
                        .district("Quận Nam Từ Liêm")
                        .districtId("3440")
                        .wardCode("13008")
                        .type(ShopAddressType.RETURN_ADDRESS)
                        .build();

                shopAddressCommandService.create(createShopAddressSendCmd);
                shopAddressCommandService.create(createShopAddressReturnRequest);
            }
        };
    }
}
