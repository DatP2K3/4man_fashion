package com.evo.common.enums;

public enum FileUsageStatus {
    TEMPORARY, // File mới tải lên, chưa được liên kết
    USED,      // File đã được liên kết với sản phẩm
    UNUSED     // File không còn được sử dụng
}
