package com.fourman.common.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> implements Serializable {
    @Builder.Default
    private PageableDTO page = new PageableDTO();

    private List<T> data;

    public PageDTO(List<T> data, int pageIndex, int pageSize, long total) {
        this.data = data;
        this.page = new PageableDTO(pageIndex, pageSize, total);
    }

    public <U> PageDTO(Page<U> pageInput, Function<List<U>, List<T>> mapper) {
        Pageable pageable = pageInput.getPageable();
        this.page = new PageableDTO(pageable.getPageNumber(), pageable.getPageSize(), pageInput.getTotalElements());
        List<T> content = mapper.apply(pageInput.getContent());
        if (content != null) {
            this.data = content;
        }
    }

    public static <T> PageDTO<T> of(List<T> data, int pageIndex, int pageSize, long total) {
        return new PageDTO<>(data, pageIndex, pageSize, total);
    }

    public static <T> PageDTO<T> empty() {
        return new PageDTO<>(new ArrayList<>(), 1, 30, 0L);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageableDTO implements Serializable {
        private int pageIndex = 0;
        private int pageSize = 0;
        private long total = 0L;
    }
}
