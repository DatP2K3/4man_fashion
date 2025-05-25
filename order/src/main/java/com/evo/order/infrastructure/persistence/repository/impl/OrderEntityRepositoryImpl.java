package com.evo.order.infrastructure.persistence.repository.impl;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import org.springframework.util.StringUtils;

import com.evo.common.enums.OrderStatus;
import com.evo.order.domain.query.SearchOrderQuery;
import com.evo.order.infrastructure.persistence.entity.OrderEntity;
import com.evo.order.infrastructure.persistence.repository.custom.OrderEntityRepositoryCustom;

public class OrderEntityRepositoryImpl implements OrderEntityRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderEntity> search(SearchOrderQuery searchOrderQuery) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select o from OrderEntity o "
                + createWhereQuery(
                        searchOrderQuery.getKeyword(),
                        searchOrderQuery.getUserId(),
                        searchOrderQuery.getOrderStatus(),
                        searchOrderQuery.getStartDate(),
                        searchOrderQuery.getEndDate(),
                        searchOrderQuery.getPrinted(),
                        values)
                + createOrderQuery(searchOrderQuery.getSortBy());
        TypedQuery<OrderEntity> query = entityManager.createQuery(sql, OrderEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((searchOrderQuery.getPageIndex() - 1) * searchOrderQuery.getPageSize());
        query.setMaxResults(searchOrderQuery.getPageSize());
        return query.getResultList();
    }

    private String createWhereQuery(
            String keyword,
            UUID userId,
            OrderStatus orderStatus,
            Instant startDate,
            Instant endDate,
            Boolean printed,
            Map<String, Object> values) {
        StringBuilder sql = new StringBuilder();
        if (!keyword.isBlank()) {
            sql.append(" where ( lower(o.orderCode) like :keyword" + " or lower(o.recipientName) like :keyword )");
            values.put("keyword", encodeKeyword(keyword));
        }
        if (userId != null) {
            if (sql.length() == 0) {
                sql.append(" where ");
            } else {
                sql.append(" and ");
            }
            sql.append(" o.userId = :userId");
            values.put("userId", userId);
        }
        if (orderStatus != null) {
            if (sql.length() == 0) {
                sql.append(" where ");
            } else {
                sql.append(" and ");
            }
            sql.append(" o.orderStatus = :orderStatus");
            values.put("orderStatus", orderStatus);
        }
        if (startDate != null) {
            if (sql.length() == 0) {
                sql.append(" where ");
            } else {
                sql.append(" and ");
            }
            sql.append(" o.createdAt >= :startDate");
            values.put("startDate", startDate);
        }
        if (endDate != null) {
            if (sql.length() == 0) {
                sql.append(" where ");
            } else {
                sql.append(" and ");
            }
            sql.append(" o.createdAt <= :endDate");
            values.put("endDate", endDate);
        }
        if (printed != null) {
            if (sql.length() == 0) {
                sql.append(" where ");
            } else {
                sql.append(" and ");
            }
            sql.append(" o.printed = :printed");
            values.put("printed", printed);
        }
        return sql.toString();
    }

    public StringBuilder createOrderQuery(String sortBy) {
        StringBuilder hql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            hql.append(" order by o.").append(sortBy.replace(".", " "));
        }
        return hql;
    }

    public String encodeKeyword(String keyword) {
        if (keyword == null) {
            return "%";
        }

        return "%" + keyword.trim().toLowerCase() + "%";
    }

    @Override
    public Long count(SearchOrderQuery searchOrderQuery) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select count(o) from OrderEntity o "
                + createWhereQuery(
                        searchOrderQuery.getKeyword(),
                        searchOrderQuery.getUserId(),
                        searchOrderQuery.getOrderStatus(),
                        searchOrderQuery.getStartDate(),
                        searchOrderQuery.getEndDate(),
                        searchOrderQuery.getPrinted(),
                        values);
        Query query = entityManager.createQuery(sql, Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
}
