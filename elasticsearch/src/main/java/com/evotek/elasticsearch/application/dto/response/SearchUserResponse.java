package com.evotek.elasticsearch.application.dto.response;

import java.util.List;

import com.evotek.elasticsearch.domain.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class SearchUserResponse {
    List<User> users;
    private int pageIndex;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;
    private boolean hasPrevious;
}
