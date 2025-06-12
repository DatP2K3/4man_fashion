package com.evo.profile.domain.query;

import java.time.Instant;
import java.util.UUID;

import com.evo.common.query.PagingQuery;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchProfileQuery extends PagingQuery {
    private String keyword;
    private UUID userId;
    private Instant createdFrom;
    private Instant createdTo;
}
