package com.evotek.elasticsearch.infrastructure.persistence.document;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import com.evo.common.enums.DiscountType;

import lombok.*;

@Document(indexName = "product")
@Setting(settingPath = "esconfig/elastic-analyzer.json")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDocumentEntity {
    @Id
    @Field(type = FieldType.Keyword)
    private UUID id;

    @MultiField(
            mainField =
                    @Field(
                            type = FieldType.Text,
                            analyzer = "autocomplete_index",
                            searchAnalyzer = "autocomplete_search"),
            otherFields = {@InnerField(suffix = "sort", type = FieldType.Keyword)})
    private String name;

    @Field(type = FieldType.Keyword)
    private Long originPrice;

    @Field(type = FieldType.Keyword)
    private Long discountPrice;

    @Field(type = FieldType.Keyword)
    private int discountPercentage;

    @Field(type = FieldType.Keyword)
    private DiscountType discountType;

    @Field(type = FieldType.Keyword)
    private UUID categoryId;

    @Field(type = FieldType.Keyword)
    private Long totalSold;

    @Field(type = FieldType.Keyword)
    private BigDecimal averageRating;

    @Field(type = FieldType.Keyword)
    private Boolean hidden;

    @Field(type = FieldType.Keyword)
    private UUID avatarId;
}
