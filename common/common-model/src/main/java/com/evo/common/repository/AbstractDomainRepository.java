package com.evo.common.repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.common.mapper.EntityMapper;

public abstract class AbstractDomainRepository<D, E, ID> implements DomainRepository<D, ID> {
    protected final JpaRepository<E, ID> repository;
    protected final EntityMapper<D, E> entityMapper;

    protected AbstractDomainRepository(JpaRepository<E, ID> repository, EntityMapper<D, E> entityMapper) {
        this.repository = repository;
        this.entityMapper = entityMapper;
    }

    @Override
    @Transactional
    public D save(D domainModel) {
        List<D> domainModels = this.saveAll(List.of(domainModel));
        return domainModels.getFirst();
    }

    @Override
    public List<D> findAllByIds(List<ID> ids) {
        return this.enrichList(this.repository.findAllById(ids).stream()
                .map(this.entityMapper::toDomainModel)
                .toList());
    }

    @Override
    @Transactional
    public List<D> saveAll(List<D> domains) {
        List<E> entities = this.entityMapper.toEntityList(domains);
        entities = this.repository.saveAll(entities);
        return this.entityMapper.toDomainModelList(entities);
    }

    protected D enrich(D d) {
        if (d == null) {
            return null;
        }
        List<D> ds = List.of(d);
        return this.enrichList(ds).getFirst();
    }

    protected List<D> enrichList(List<D> ds) {
        return ds;
    }
}
