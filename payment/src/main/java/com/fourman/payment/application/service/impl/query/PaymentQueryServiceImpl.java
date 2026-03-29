package com.fourman.payment.application.service.impl.query;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.common.dto.request.GetPaymentUrlRequest;
import com.fourman.payment.application.config.VNPAYConfig;
import com.fourman.payment.application.service.PaymentQueryService;
import com.fourman.payment.infrastructure.support.VNPayUtil;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PaymentQueryServiceImpl implements PaymentQueryService {
    private static final long VNPAY_AMOUNT_MULTIPLIER = 100L;
    private final VNPAYConfig vnPayConfig;

    @Override
    public String getPaymentUrl(GetPaymentUrlRequest getPaymentUrlRequest, HttpServletRequest request) {
        long amount = getPaymentUrlRequest.getTotalPrice() * VNPAY_AMOUNT_MULTIPLIER;
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        vnpParamsMap.put("vnp_TxnRef", getPaymentUrlRequest.getOrderCode());
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang: " + getPaymentUrlRequest.getOrderCode());
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        return vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    }
}
