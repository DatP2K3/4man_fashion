package com.evo.common;

import java.time.Instant;
import java.util.UUID;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Auditor {
    protected String createdBy;
    protected UUID lastModifiedBy;
    protected Instant createdAt;
}
