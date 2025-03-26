package com.evo.product.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name = "product_statistics")
public class ProductStatisticsEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "total_sold")
    private Long totalSold;

    @Column(name = "average_rating", precision = 2, scale = 1)
    private BigDecimal averageRating;
}

/* TODO: Không cập nhật average_rating liên tục, nếu có đánh giá mới thì lưu lại product_id vào redis, sau 1 khoảng thời
    gian nhất định thì cập nhật average_rating để tránh việc cập nhật liên tục, kêt shợp với kafka để bắt sự kiện đánh
    giá mới, (việc cập nhật như vậy gọi là batch update) */

