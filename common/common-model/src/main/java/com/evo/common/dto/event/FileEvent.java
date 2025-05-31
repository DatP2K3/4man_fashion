package com.evo.common.dto.event;

import com.evo.common.enums.FileUsageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEvent {
    Map<UUID, FileUsageStatus> fileUsageStatusMap;
}
