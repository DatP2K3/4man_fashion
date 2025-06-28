package com.evo.product.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import jakarta.persistence.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.evo.common.entity.AuditEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products")
public class ProductEntity extends AuditEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "category_id")
    private UUID categoryId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "description", columnDefinition = "jsonb")
    private Map<String, String> description;

    @Column(name = "total_sold")
    private Long totalSold;

    @Column(name = "average_rating", precision = 2, scale = 1)
    private BigDecimal averageRating;

    @Column(name = "origin_price")
    private Long originPrice;

    @Column(name = "weight")
    private int weight;

    @Column(name = "length")
    private int length;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "introduce", columnDefinition = "TEXT")
    private String introduce;

    @Column(name = "hidden")
    private Boolean hidden;
}

/* TODO: Không cập nhật average_rating liên tục, nếu có đánh giá mới thì lưu lại product_id vào redis, sau 1 khoảng thời
gian nhất định thì cập nhật average_rating để tránh việc cập nhật liên tục, kêt shợp với kafka để bắt sự kiện đánh
giá mới, (việc cập nhật như vậy gọi là batch update) */
