package com.evo.order.domain.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.evo.common.dto.response.*;
import com.evo.common.enums.OrderStatus;
import com.evo.common.enums.PaymentMethod;
import com.evo.common.enums.PaymentStatus;
import com.evo.order.application.dto.response.OrderFeeDTO;

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

        this.fromName = fromAddress.getShopName();
        this.fromPhoneNumber = fromAddress.getPhoneNumber();
        this.fromAddressLine1 = fromAddress.getAddressLine1();
        this.fromAddressLine2 = fromAddress.getAddressLine2();
        this.fromWard = fromAddress.getWard();
        this.fromWardCode = fromAddress.getWardCode();
        this.fromDistrict = fromAddress.getDistrict();
        this.fromDistrictId = fromAddress.getDistrictId();
        this.fromCity = fromAddress.getCity();

        this.toName = profileDTO.getFirstName() + profileDTO.getLastName();
        this.toPhoneNumber = toAddress.getPhoneNumber();
        this.toAddressLine1 = toAddress.getAddressLine1();
        this.toAddressLine2 = toAddress.getAddressLine2();
        this.toWard = toAddress.getWard();
        this.toWardCode = toAddress.getWardCode();
        this.toDistrict = toAddress.getDistrict();
        this.toDistrictId = toAddress.getDistrictId();
        this.toCity = toAddress.getCity();

        this.returnName = returnAddress.getShopName();
        this.returnPhoneNumber = returnAddress.getPhoneNumber();
        this.returnAddressLine1 = returnAddress.getAddressLine1();
        this.returnAddressLine2 = returnAddress.getAddressLine2();
        this.returnWard = returnAddress.getWard();
        this.returnWardCode = returnAddress.getWardCode();
        this.returnDistrict = returnAddress.getDistrict();
        this.returnDistrictId = returnAddress.getDistrictId();
        this.returnCity = returnAddress.getCity();

        this.shipmentFee = orderFeeDTO.getShippingFee();
        this.totalPrice = orderFeeDTO.getTotalPrice();
        this.cashbackUsed = orderFeeDTO.getCashbackUsed();
        this.totalWeight = orderFeeDTO.getTotalWeight();
        this.totalHeight = orderFeeDTO.getTotalHeight();
        this.totalWidth = orderFeeDTO.getTotalWidth();
        this.totalLength = orderFeeDTO.getTotalLength();

        List<CartItemDTO> cartItemDTOS = cartDTO.getCartItems();
        if (this.orderItems == null) {
            this.orderItems = new ArrayList<>();
        }
        this.totalProductVariant = cartItemDTOS.size();
        for (CartItemDTO cartItemDTO : cartItemDTOS) {
            if (cartItemDTO.getDeleted() == true) continue;
            CreateOrderItemCmd orderItem = new CreateOrderItemCmd();
            orderItem.setProductId(cartItemDTO.getProductId());
            orderItem.setProductVariantId(cartItemDTO.getProductVariantId());
            orderItem.setQuantity(cartItemDTO.getQuantity());
            orderItem.setPrice(
                    cartItemDTO.getDiscountPrice() != null && cartItemDTO.getDiscountPrice() != 0
                            ? cartItemDTO.getDiscountPrice()
                            : cartItemDTO.getOriginPrice());
            this.orderItems.add(orderItem);
        }
    }
}
