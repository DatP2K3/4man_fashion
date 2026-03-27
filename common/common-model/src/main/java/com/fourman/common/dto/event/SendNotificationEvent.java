package com.fourman.common.dto.event;

import java.util.Map;

import com.fourman.common.enums.TemplateCode;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendNotificationEvent {
    private String channel;
    private String recipient;
    private TemplateCode templateCode;
    private Map<String, Object> param;
}
