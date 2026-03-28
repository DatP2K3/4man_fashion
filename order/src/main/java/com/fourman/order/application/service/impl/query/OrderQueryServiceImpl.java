package com.fourman.order.application.service.impl.query;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fourman.common.dto.request.SearchOrderRequest;
import com.fourman.common.dto.response.*;
import com.fourman.common.enums.ShopAddressType;
import com.fourman.order.application.dto.mapper.OrderDTOMapper;
import com.fourman.order.application.dto.request.GetGHNFeeRequest;
import com.fourman.order.application.dto.request.PrintOrCancelGHNOrderRequest;
import com.fourman.order.application.dto.response.*;
import com.fourman.order.application.mapper.QueryMapper;
import com.fourman.order.application.service.OrderQueryService;
import com.fourman.order.domain.Order;
import com.fourman.order.domain.query.SearchOrderQuery;
import com.fourman.order.domain.repository.OrderDomainRepository;
import com.fourman.order.infrastructure.adapter.cart.client.CartClient;
import com.fourman.order.infrastructure.adapter.ghn.client.GHNClient;
import com.fourman.order.infrastructure.adapter.profile.client.ProfileClient;
import com.fourman.order.infrastructure.adapter.shopinfo.client.ShopInfoClient;
import com.fourman.order.infrastructure.persistence.entity.OrderEntity;
import com.fourman.order.infrastructure.persistence.repository.OrderEntityRepository;

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
    public PageDTO<OrderDTO> search(SearchOrderRequest request) {
        SearchOrderQuery searchOrderQuery = queryMapper.from(request);
        Long total = this.count(searchOrderQuery);
        if (total == 0) {
            return PageDTO.empty();
        }
        List<OrderEntity> orderEntities = orderEntityRepository.search(searchOrderQuery);
        List<OrderDTO> orderDTOs = orderDTOMapper.entitiesToDTOs(orderEntities);
        return PageDTO.of(orderDTOs, searchOrderQuery.getPageIndex(), searchOrderQuery.getPageSize(), total);
    }

    @Override
    public Long count(SearchOrderQuery searchOrderQuery) {
        return orderEntityRepository.count(searchOrderQuery);
    }

    @Override
    public OrderFeeDTO calculateFeeByAddressId(UUID toAddressId) {
        CartDTO cartDTO = cartClient.getCart().getData();
        List<CartItemDTO> cartItems = cartDTO.getCartItems();
        Long cashbackUsed = 0L;
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
        Long cashbackBalance = profileDTO.getCashbackBalance();
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

        if (cashbackBalance < ghnFeeDTO.getTotal() + totalPrice) {
            cashbackUsed = cashbackBalance;
        } else {
            cashbackUsed = cashbackBalance - (ghnFeeDTO.getTotal() + totalPrice);
        }
        return OrderFeeDTO.builder()
                .totalQuantity(totalQuantity)
                .cashbackUsed(cashbackUsed)
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
        String token = getGHNPrintToken(printOrCancelGHNOrderRequest);
        return ghnClient.print(token).getData();
    }

    @Override
    public String getGHNPrintToken(PrintOrCancelGHNOrderRequest printOrCancelGHNOrderRequest) {
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
