package com.fourman.common.dto.response;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagingResponse<T> extends Response<List<T>> {
    private PageableResponse page = new PageableResponse();

    public PagingResponse() {}

    public PagingResponse(List<T> data, int pageIndex, int pageSize, long total) {
        this.page.setPageIndex(pageIndex);
        this.page.setPageSize(pageSize);
        this.page.setTotal(total);
        this.data = data;
        this.success();
    }

    public PagingResponse(PageDTO<T> pageInput) {
        this.data = pageInput.getData();
        this.page.setPageIndex(pageInput.getPage().getPageIndex());
        this.page.setPageSize(pageInput.getPage().getPageSize());
        this.page.setTotal(pageInput.getPage().getTotal());
        this.success();
    }

    public <U> PagingResponse(PageDTO<U> pageInput, Function<List<U>, List<T>> mapper) {
        PageDTO.PageableDTO pageable = pageInput.getPage();
        this.page.setPageIndex(pageable.getPageIndex());
        this.page.setPageSize(pageable.getPageSize());
        this.page.setTotal(pageable.getTotal());
        List<T> content = (List) mapper.apply(pageInput.getData());
        if (content != null) {
            this.data = content;
        }
    }

    public static <T> PagingResponse<T> of(List<T> data, int pageIndex, int pageSize, long total) {
        return new PagingResponse<T>(data, pageIndex, pageSize, total);
    }

    public static <T> PagingResponse<T> of(PageDTO<T> pageInput) {
        return new PagingResponse<T>(pageInput);
    }

    public static <T> PagingResponse<T> failPaging(RuntimeException exception) {
        PagingResponse<T> response = new PagingResponse<T>();
        response.setSuccess(false);
        response.setException(exception);
        return response;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PagingResponse)) {
            return false;
        } else {
            PagingResponse<?> other = (PagingResponse) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
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

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PagingResponse;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Object $page = this.getPage();
        result = result * 59 + ($page == null ? 43 : $page.hashCode());
        return result;
    }

    public PageableResponse getPage() {
        return this.page;
    }

    public void setPage(final PageableResponse page) {
        this.page = page;
    }

    public String toString() {
        return "PagingResponse(page=" + String.valueOf(this.getPage()) + ")";
    }

    public static class PageableResponse implements Serializable {
        private int pageIndex;
        private int pageSize;
        private long total;

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
            } else if (!(o instanceof PageableResponse)) {
                return false;
            } else {
                PageableResponse other = (PageableResponse) o;
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
            return other instanceof PageableResponse;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            result = result * 59 + this.getPageIndex();
            result = result * 59 + this.getPageSize();
            long $total = this.getTotal();
            result = result * 59 + (int) ($total >>> 32 ^ $total);
            return result;
        }

        public String toString() {
            int var10000 = this.getPageIndex();
            return "PagingResponse.PageableResponse(pageIndex=" + var10000 + ", pageSize=" + this.getPageSize()
                    + ", total=" + this.getTotal() + ")";
        }
    }
}
