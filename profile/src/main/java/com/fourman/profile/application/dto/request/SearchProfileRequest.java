package com.fourman.profile.application.dto.request;

import java.time.Instant;

import com.fourman.common.dto.request.PagingRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProfileRequest extends PagingRequest {
    private String keyword;
    private Instant createdFrom;
    private Instant createdTo;
}
