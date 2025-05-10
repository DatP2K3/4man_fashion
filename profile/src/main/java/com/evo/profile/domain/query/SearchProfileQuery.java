package com.evo.profile.domain.query;

import com.evo.common.query.PagingQuery;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SearchProfileQuery extends PagingQuery {
    private String keyword;
    private UUID userId;
}
