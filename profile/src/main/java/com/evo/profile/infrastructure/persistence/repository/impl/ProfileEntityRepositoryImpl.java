package com.evo.profile.infrastructure.persistence.repository.impl;

import com.evo.profile.domain.query.SearchProfileQuery;
import com.evo.profile.infrastructure.persistence.entity.ProfileEntity;
import com.evo.profile.infrastructure.persistence.repository.custom.ProfileEntityRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileEntityRepositoryImpl implements ProfileEntityRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProfileEntity> search(SearchProfileQuery searchProfileQuery) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select e from ProfileEntity e " + createWhereQuery(searchProfileQuery.getKeyword(), values)
                + createOrderQuery(searchProfileQuery.getSortBy());
        TypedQuery<ProfileEntity> query = entityManager.createQuery(sql, ProfileEntity.class);
        values.forEach(query::setParameter);
        query.setFirstResult((searchProfileQuery.getPageIndex() - 1) * searchProfileQuery.getPageSize());
        query.setMaxResults(searchProfileQuery.getPageSize());
        return query.getResultList();
    }

    private String createWhereQuery(String keyword, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder();
        if (!keyword.isBlank()) {
            sql.append(" and ( lower(e.username) like :keyword" + " or lower(e.email) like :keyword )");
            values.put("keyword", encodeKeyword(keyword));
        }
        return sql.toString();
    }

    public StringBuilder createOrderQuery(String sortBy) {
        StringBuilder hql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            hql.append(" order by e.").append(sortBy.replace(".", " "));
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
    public Long count(SearchProfileQuery searchProfileQuery) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select count(e) from ProfileEntity e " + createWhereQuery(searchProfileQuery.getKeyword(), values);
        Query query = entityManager.createQuery(sql, Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
}
