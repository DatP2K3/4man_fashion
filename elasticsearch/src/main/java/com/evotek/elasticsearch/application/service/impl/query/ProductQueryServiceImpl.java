package com.evotek.elasticsearch.application.service.impl.query;

import java.util.List;

import com.evotek.elasticsearch.infrastructure.persistence.document.ProductDocumentEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.evotek.elasticsearch.application.dto.request.SearchProductRequest;
import com.evotek.elasticsearch.application.dto.response.SearchProductDTO;
import com.evotek.elasticsearch.application.service.impl.ProductQueryService;
import com.evotek.elasticsearch.infrastructure.persistence.mapper.ProductDocumentMapper;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ElasticsearchOperations elasticsearchOperations;
    private final ProductDocumentMapper productDocumentMapper;

    @Override
    public SearchProductDTO searchProduct(SearchProductRequest request) {
        Query query = Query.of(q -> q.bool(boolBuilder -> {
            // Tìm kiếm theo searchTerm nếu có
            if (StringUtils.hasText(request.getKeyword())) {
                // Thêm điều kiện tìm kiếm cho name với trọng số cao
                boolBuilder.must(mustQuery -> mustQuery.matchPhrasePrefix(matchQuery ->
                        matchQuery.field("name").query(request.getKeyword()).boost(2.0f)));
            }

            // Lọc theo role nếu có
            if (request.getCategoryId() != null) {
                boolBuilder.filter(filterQuery -> filterQuery.term(termQuery ->
                        termQuery.field("roleIds").value(request.getCategoryId().toString())));
            }

            // Lọc theo trạng thái locked nếu có
            if (request.getHidden() != null) {
                boolBuilder.filter(filterQuery ->
                        filterQuery.term(termQuery -> termQuery.field("hidden").value(request.getHidden())));
            }

            return boolBuilder;
        }));

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(query)
                .withPageable(PageRequest.of(
                        request.getPage(),
                        request.getSize(),
                        Sort.by(
                                request.getSortDirection().equalsIgnoreCase("desc")
                                        ? Sort.Direction.DESC
                                        : Sort.Direction.ASC,
                                request.getSortField())))
                .build();

        // Thực hiện tìm kiếm®
        SearchHits<ProductDocumentEntity> searchHits = elasticsearchOperations.search(searchQuery, ProductDocumentEntity.class);

        // Xử lý kết quả
        List<ProductDocumentEntity> productDocumentEntities =
                searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();

        // Tạo và trả về response
        return SearchProductDTO.builder()
                .users(productDocumentMapper.toDomainModelList(productDocumentEntities))
                .totalElements(searchHits.getTotalHits())
                .totalPages((int) Math.ceil((double) searchHits.getTotalHits() / request.getSize()))
                .pageIndex(request.getPage())
                .hasNext((request.getPage() * 1L) * request.getSize() < searchHits.getTotalHits())
                .hasPrevious(request.getPage() > 0)
                .build();
    }
}

