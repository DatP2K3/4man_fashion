package com.evo.common.dto.event;

import com.evo.common.dto.request.SyncProductRequest;
import com.evo.common.enums.SyncProductType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncProductEvent {
    SyncProductType syncProductType;
    SyncProductRequest syncProductRequest;
}
