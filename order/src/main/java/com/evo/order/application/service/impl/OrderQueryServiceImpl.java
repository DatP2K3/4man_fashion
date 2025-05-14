package com.evo.order.application.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.evo.common.dto.response.*;
import com.evo.common.enums.ShopAddressType;
import com.evo.order.application.dto.mapper.OrderDTOMapper;
import com.evo.order.application.dto.request.GetGHNFeeRequest;
import com.evo.order.application.dto.request.PrintOrCancelGHNOrderRequest;
import com.evo.order.application.dto.request.SearchOrderRequest;
import com.evo.order.application.dto.response.*;
import com.evo.order.application.mapper.QueryMapper;
import com.evo.order.application.service.OrderQueryService;
import com.evo.order.domain.Order;
import com.evo.order.domain.query.SearchOrderQuery;
import com.evo.order.domain.repository.OrderDomainRepository;
import com.evo.order.infrastructure.adapter.cart.client.CartClient;
import com.evo.order.infrastructure.adapter.ghn.client.GHNClient;
import com.evo.order.infrastructure.adapter.profile.client.ProfileClient;
import com.evo.order.infrastructure.adapter.shopinfo.client.ShopInfoClient;
import com.evo.order.infrastructure.persistence.entity.OrderEntity;
import com.evo.order.infrastructure.persistence.repository.OrderEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {
    private final CartClient cartClient;
    private final ShopInfoClient shopInfoClient;
    private final ProfileClient profileClient;
    private final GHNClient ghnClient;
    private final QueryMapper queryMapper;
    private final OrderDTOMapper orderDTOMapper;
    private final OrderDomainRepository orderDomainRepository;
    private final OrderEntityRepository orderEntityRepository;

    @Override
    public List<OrderDTO> search(SearchOrderRequest request) {
        SearchOrderQuery searchOrderQuery = queryMapper.from(request);
        List<OrderEntity> orderEntities = orderEntityRepository.search(searchOrderQuery);
        return orderDTOMapper.entitiesToDTOs(orderEntities);
    }

    @Override
    public Long count(SearchOrderRequest request) {
        SearchOrderQuery searchOrderQuery = queryMapper.from(request);
        return orderEntityRepository.count(searchOrderQuery);
    }

    @Override
    public OrderFeeDTO caculateFeeByAddressId(UUID toAddressId) {
        CartDTO cartDTO = cartClient.getCart().getData();
        List<CartItemDTO> cartItems = cartDTO.getCartItems();
        Long totalPrice = 0L;
        int totalQuantity = cartItems.size();
        int totalWeight = 0;
        int totalHeight = 0;
        int maxWidth = 0;
        int maxLength = 0;
        for (CartItemDTO cartItemDTO : cartItems) {
            if (cartItemDTO.getDeleted() == true) continue;
            if (cartItemDTO.getDiscountPrice() != null && cartItemDTO.getDiscountPrice() > 0) {
                totalPrice += cartItemDTO.getQuantity() * cartItemDTO.getDiscountPrice();
            } else {
                totalPrice += cartItemDTO.getQuantity() * cartItemDTO.getOriginPrice();
            }
            totalWeight += cartItemDTO.getQuantity() * cartItemDTO.getWeight();
            totalHeight += cartItemDTO.getQuantity() * cartItemDTO.getHeight();
            maxWidth = Math.max(maxWidth, cartItemDTO.getWidth());
            maxLength = Math.max(maxLength, cartItemDTO.getLength());
        }

        List<ShopAddressDTO> shopAddressDTOS = shopInfoClient.getShopAddress().getData();
        ShopAddressDTO addressForSend = shopAddressDTOS.stream()
                .filter(item -> item.getType().equals(ShopAddressType.SEND_ADDRESS))
                .findFirst()
                .orElse(null);

        ProfileDTO profileDTO = profileClient.getProfile().getData();
        ShippingAddressDTO toAddress = profileDTO.getListShippingAddress().stream()
                .filter(item -> item.getId().equals(toAddressId))
                .findFirst()
                .orElse(null);

        GetGHNFeeRequest getGHNFeeRequest = GetGHNFeeRequest.builder()
                .FromDistrictId(Integer.parseInt(addressForSend.getDistrictId()))
                .FromWardCode(addressForSend.getWardCode())
                .ToDistrictId(Integer.parseInt(toAddress.getDistrictId()))
                .ToWardCode(toAddress.getWardCode())
                .Length(maxLength)
                .Width(maxWidth)
                .Height(totalHeight)
                .Weight(totalWeight)
                .InsuranceValue(totalPrice)
                .build();

        GHNFeeDTO ghnFeeDTO = ghnClient.calculateShippingFee(getGHNFeeRequest).getData();

        return OrderFeeDTO.builder()
                .totalQuantity(totalQuantity)
                .totalPrice(totalPrice)
                .shippingFee(ghnFeeDTO.getTotal())
                .totalLength(maxLength)
                .totalWeight(totalWeight)
                .totalHeight(totalHeight)
                .totalWidth(maxWidth)
                .build();
    }

    @Override
    public OrderDTO findByOrderCode(String orderCode) {
        Order order = orderDomainRepository.findByOrderCode(orderCode);
        return orderDTOMapper.domainModelToDTO(order);
    }

    @Override
    public String printGHNOrder(PrintOrCancelGHNOrderRequest printOrCancelGHNOrderRequest) {
        String token = getGHYNPrintToken(printOrCancelGHNOrderRequest);
        return ghnClient.print(token);
    }

    @Override
    public String getGHYNPrintToken(PrintOrCancelGHNOrderRequest printOrCancelGHNOrderRequest) {
        GHNPrintTokenDTO ghnPrintTokenDTO =
                ghnClient.getPrintToken(printOrCancelGHNOrderRequest).getData();
        return ghnPrintTokenDTO.getToken();
    }

    @Override
    public List<OrderDTO> getOrdersOfUser() {
        var context = SecurityContextHolder.getContext();
        UUID userId = UUID.fromString(context.getAuthentication().getName());
        List<OrderEntity> orderEntities = orderEntityRepository.findByUserId(userId);
        return orderDTOMapper.entitiesToDTOs(orderEntities);
    }
}
