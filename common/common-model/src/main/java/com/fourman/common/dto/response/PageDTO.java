package com.fourman.common.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> implements Serializable {
    private PageableDTO page = new PageableDTO();
    private List<T> data;

    public PageDTO(List<T> data, int pageIndex, int pageSize, long total) {
        this.data = data;
        this.page.setPageIndex(pageIndex);
        this.page.setPageSize(pageSize);
        this.page.setTotal(total);
    }

    public <U> PageDTO(Page<U> pageInput, Function<List<U>, List<T>> mapper) {
        Pageable pageable = pageInput.getPageable();
        this.page.setPageIndex(pageable.getPageNumber());
        this.page.setPageSize(pageable.getPageSize());
        this.page.setTotal(pageInput.getTotalElements());
        List<T> content = (List) mapper.apply(pageInput.getContent());
        if (content != null) {
            this.data = content;
        }
    }

    public static <T> PageDTO<T> of(List<T> data, int pageIndex, int pageSize, long total) {
        return new PageDTO<T>(data, pageIndex, pageSize, total);
    }

    public static <T> PageDTO<T> empty() {
        return new PageDTO<T>(new ArrayList(), 1, 30, 0L);
    }

    public PageableDTO getPage() {
        return this.page;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setPage(final PageableDTO page) {
        this.page = page;
    }

    public void setData(final List<T> data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageDTO)) {
            return false;
        } else {
            PageDTO<?> other = (PageDTO) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$page = this.getPage();
                Object other$page = other.getPage();
                if (this$page == null) {
                    if (other$page != null) {
                        return false;
                    }
                } else if (!this$page.equals(other$page)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageDTO;
    }

    public int hashCode() {
        int result = 1;
        Object $page = this.getPage();
        result = result * 59 + ($page == null ? 43 : $page.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        String var10000 = String.valueOf(this.getPage());
        return "PageDTO(page=" + var10000 + ", data=" + String.valueOf(this.getData()) + ")";
    }

    public static class PageableDTO implements Serializable {
        private int pageIndex = 0;
        private int pageSize = 0;
        private long total = 0L;

        public int getPageIndex() {
            return this.pageIndex;
        }

        public int getPageSize() {
            return this.pageSize;
        }

        public long getTotal() {
            return this.total;
        }

        public void setPageIndex(final int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public void setPageSize(final int pageSize) {
            this.pageSize = pageSize;
        }

        public void setTotal(final long total) {
            this.total = total;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof PageableDTO)) {
                return false;
            } else {
                PageableDTO other = (PageableDTO) o;
                if (!other.canEqual(this)) {
                    return false;
                } else if (this.getPageIndex() != other.getPageIndex()) {
                    return false;
                } else if (this.getPageSize() != other.getPageSize()) {
                    return false;
                } else {
                    return this.getTotal() == other.getTotal();
                }
            }
        }

        protected boolean canEqual(final Object other) {
            return other instanceof PageableDTO;
        }

        public int hashCode() {
            int result = 1;
            result = result * 59 + this.getPageIndex();
            result = result * 59 + this.getPageSize();
            long $total = this.getTotal();
            result = result * 59 + (int) ($total >>> 32 ^ $total);
            return result;
        }

        public String toString() {
            int var10000 = this.getPageIndex();
            return "PageDTO.PageableDTO(pageIndex=" + var10000 + ", pageSize=" + this.getPageSize() + ", total="
                    + this.getTotal() + ")";
        }
    }
}
