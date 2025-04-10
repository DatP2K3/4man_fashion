package com.evo.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KafkaTopic {
    SEND_NOTIFICATION_GROUP("send-notification-group"),
    PUSH_NOTIFICATION_GROUP("push-notification-group"),
    SYNC_PRODUCT_GROUP("sync-product-group");
    private final String topicName;
}
