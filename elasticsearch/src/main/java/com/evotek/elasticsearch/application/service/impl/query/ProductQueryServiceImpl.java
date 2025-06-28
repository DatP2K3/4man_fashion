package com.evotek.elasticsearch.application.service.impl.query;

import java.util.List;

import com.evo.common.dto.response.PageDTO;
import com.evotek.elasticsearch.application.dto.response.ProductDocumentDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.evotek.elasticsearch.application.dto.mapper.ProductDocumentDTOMapper;
import com.evotek.elasticsearch.application.dto.request.SearchProductRequest;
import com.evotek.elasticsearch.application.service.ProductQueryService;
import com.evotek.elasticsearch.infrastructure.persistence.document.ProductDocumentEntity;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ElasticsearchOperations elasticsearchOperations;
    private final ProductDocumentDTOMapper productDocumentDTOMapper;

    @Override
    public PageDTO<ProductDocumentDTO> searchProduct(SearchProductRequest request) {
        Query query = Query.of(q -> q.bool(boolBuilder -> {
            // Tìm kiếm theo searchTerm nếu có
            if (StringUtils.hasText(request.getKeyword())) {
                // Thêm điều kiện tìm kiếm cho name với trọng số cao
                boolBuilder.must(mustQuery -> mustQuery.matchPhrasePrefix(matchQuery ->
                        matchQuery.field("name").query(request.getKeyword()).boost(2.0f)));
            }

            if (request.getCategoryId() != null) {
                boolBuilder.filter(filterQuery -> filterQuery.term(termQuery -> termQuery
                        .field("categoryId")
                        .value(request.getCategoryId().toString())));
            }

            if (request.getHidden() != null) {
                boolBuilder.filter(filterQuery ->
                        filterQuery.term(termQuery -> termQuery.field("hidden").value(request.getHidden())));
            }

            return boolBuilder;
        }));

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(query)
                .withPageable(PageRequest.of(
                        request.getPageIndex() - 1,
                        request.getPageSize(),
                        Sort.by(
                                request.getSortDirection().equalsIgnoreCase("desc")
                                        ? Sort.Direction.DESC
                                        : Sort.Direction.ASC,
                                request.getSortBy())))
                .build();

        // Thực hiện tìm kiếm®
        SearchHits<ProductDocumentEntity> searchHits =
                elasticsearchOperations.search(searchQuery, ProductDocumentEntity.class);

        if (searchHits.getTotalHits() == 0) {
            return PageDTO.empty();
        }

        // Xử lý kết quả
        List<ProductDocumentEntity> productDocumentEntities =
                searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();

        List<ProductDocumentDTO> productDocumentDTOs = productDocumentDTOMapper.entitiesToDTOs(productDocumentEntities);

        return PageDTO.of(productDocumentDTOs, request.getPageIndex(), request.getPageSize(), searchHits.getTotalHits());
    }

    @Override
    public List<String> autocompleteProductNames(String keyword, int limit) {
        if (!StringUtils.hasText(keyword)) {
            return List.of(); // Trả về rỗng nếu không có từ khóa
        }

        Query query = Query.of(q -> q.match(m -> m.field("name") // hoặc "name" nếu không dùng multi-field
                .query(keyword)));

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(query)
                .withPageable(PageRequest.of(0, limit))
                .build();

        SearchHits<ProductDocumentEntity> hits =
                elasticsearchOperations.search(nativeQuery, ProductDocumentEntity.class);

        return hits.getSearchHits().stream()
                .map(hit -> hit.getContent().getName())
                .distinct()
                .toList();
    }
}
