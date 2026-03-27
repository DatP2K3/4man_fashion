package com.fourman.order.domain.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fourman.common.dto.response.*;
import com.fourman.common.enums.OrderStatus;
import com.fourman.common.enums.PaymentMethod;
import com.fourman.common.enums.PaymentStatus;
import com.fourman.order.application.dto.response.OrderFeeDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderCmd {
    private UUID id;
    private UUID userId;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String fromName;
    private String fromPhoneNumber;
    private String fromAddressLine1;
    private String fromAddressLine2;
    private String fromWard;
    private String fromWardCode;
    private String fromDistrict;
    private String fromDistrictId;
    private String fromCity;
    private String toName;
    private String toPhoneNumber;
    private String toAddressLine1;
    private String toAddressLine2;
    private String toWard;
    private String toWardCode;
    private String toDistrict;
    private String toDistrictId;
    private String toCity;
    private String returnName;
    private String returnPhoneNumber;
    private String returnAddressLine1;
    private String returnAddressLine2;
    private String returnWard;
    private String returnWardCode;
    private String returnDistrict;
    private String returnDistrictId;
    private String returnCity;
    private int totalProductVariant;
    private int shipmentFee;
    private Long totalPrice;
    private Long cashbackUsed;
    private String rejectReason;
    private String note;
    private UUID referencesId;
    private int totalWeight;
    private int totalHeight;
    private int totalWidth;
    private int totalLength;
    private List<CreateOrderItemCmd> orderItems;

    public void enrichInfo(
            ProfileDTO profileDTO,
            OrderFeeDTO orderFeeDTO,
            ShopAddressDTO fromAddress,
            ShippingAddressDTO toAddress,
            ShopAddressDTO returnAddress,
            CartDTO cartDTO) {
        this.userId = profileDTO.getId();
        enrichFromAddress(fromAddress);
        enrichToAddress(profileDTO, toAddress);
        enrichReturnAddress(returnAddress);
        enrichFeeInfo(orderFeeDTO);
        enrichOrderItems(cartDTO);
    }

    private void enrichFromAddress(ShopAddressDTO address) {
        this.fromName = address.getShopName();
        this.fromPhoneNumber = address.getPhoneNumber();
        this.fromAddressLine1 = address.getAddressLine1();
        this.fromAddressLine2 = address.getAddressLine2();
        this.fromWard = address.getWard();
        this.fromWardCode = address.getWardCode();
        this.fromDistrict = address.getDistrict();
        this.fromDistrictId = address.getDistrictId();
        this.fromCity = address.getCity();
    }

    private void enrichToAddress(ProfileDTO profile, ShippingAddressDTO address) {
        this.toName = profile.getFirstName() + profile.getLastName();
        this.toPhoneNumber = address.getPhoneNumber();
        this.toAddressLine1 = address.getAddressLine1();
        this.toAddressLine2 = address.getAddressLine2();
        this.toWard = address.getWard();
        this.toWardCode = address.getWardCode();
        this.toDistrict = address.getDistrict();
        this.toDistrictId = address.getDistrictId();
        this.toCity = address.getCity();
    }

    private void enrichReturnAddress(ShopAddressDTO address) {
        this.returnName = address.getShopName();
        this.returnPhoneNumber = address.getPhoneNumber();
        this.returnAddressLine1 = address.getAddressLine1();
        this.returnAddressLine2 = address.getAddressLine2();
        this.returnWard = address.getWard();
        this.returnWardCode = address.getWardCode();
        this.returnDistrict = address.getDistrict();
        this.returnDistrictId = address.getDistrictId();
        this.returnCity = address.getCity();
    }

    private void enrichFeeInfo(OrderFeeDTO fee) {
        this.shipmentFee = fee.getShippingFee();
        this.totalPrice = fee.getTotalPrice();
        this.cashbackUsed = fee.getCashbackUsed();
        this.totalWeight = fee.getTotalWeight();
        this.totalHeight = fee.getTotalHeight();
        this.totalWidth = fee.getTotalWidth();
        this.totalLength = fee.getTotalLength();
    }

    private void enrichOrderItems(CartDTO cartDTO) {
        List<CartItemDTO> cartItems = cartDTO.getCartItems();
        if (this.orderItems == null) {
            this.orderItems = new ArrayList<>();
        }
        this.totalProductVariant = cartItems.size();
        for (CartItemDTO item : cartItems) {
            if (Boolean.TRUE.equals(item.getDeleted())) continue;
            CreateOrderItemCmd orderItem = new CreateOrderItemCmd();
            orderItem.setProductId(item.getProductId());
            orderItem.setProductVariantId(item.getProductVariantId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(resolvePrice(item));
            this.orderItems.add(orderItem);
        }
    }

    private Long resolvePrice(CartItemDTO item) {
        return (item.getDiscountPrice() != null && item.getDiscountPrice() != 0)
                ? item.getDiscountPrice()
                : item.getOriginPrice();
    }
}
