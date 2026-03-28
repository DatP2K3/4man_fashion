package com.fourman.product.infrastructure.domainrepository;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.fourman.common.exception.ResponseException;
import com.fourman.common.repository.AbstractDomainRepository;
import com.fourman.product.domain.Category;
import com.fourman.product.domain.TagDescription;
import com.fourman.product.domain.exception.NotFoundError;
import com.fourman.product.domain.repository.CategoryDomainRepository;
import com.fourman.product.infrastructure.persistence.entity.CategoryEntity;
import com.fourman.product.infrastructure.persistence.entity.TagDescriptionEntity;
import com.fourman.product.infrastructure.persistence.mapper.CategoryEntityMapper;
import com.fourman.product.infrastructure.persistence.mapper.TagDescriptionEntityMapper;
import com.fourman.product.infrastructure.persistence.repository.CategoryEntityRepository;
import com.fourman.product.infrastructure.persistence.repository.TagDescriptionEntityRepository;

@Repository
public class CategoryDomainRepositoryImpl extends AbstractDomainRepository<Category, CategoryEntity, UUID>
        implements CategoryDomainRepository {
    private final CategoryEntityMapper categoryEntityMapper;
    private final CategoryEntityRepository categoryEntityRepository;
    private final TagDescriptionEntityRepository tagDescriptionEntityRepository;
    private final TagDescriptionEntityMapper tagDescriptionEntityMapper;

    public CategoryDomainRepositoryImpl(
            CategoryEntityMapper categoryEntityMapper,
            CategoryEntityRepository categoryEntityRepository,
            TagDescriptionEntityRepository tagDescriptionEntityRepository,
            TagDescriptionEntityMapper tagDescriptionEntityMapper) {
        super(categoryEntityRepository, categoryEntityMapper);
        this.categoryEntityMapper = categoryEntityMapper;
        this.categoryEntityRepository = categoryEntityRepository;
        this.tagDescriptionEntityRepository = tagDescriptionEntityRepository;
        this.tagDescriptionEntityMapper = tagDescriptionEntityMapper;
    }

    @Override
    public Category getById(UUID uuid) {
        CategoryEntity categoryEntity = categoryEntityRepository
                .findById(uuid)
                .orElseThrow(() -> new ResponseException(NotFoundError.CATEGORY_NOT_FOUND));
        return enrich(categoryEntityMapper.toDomainModel(categoryEntity));
    }

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);
        List<TagDescriptionEntity> tagDescriptionEntities =
                tagDescriptionEntityMapper.toEntityList(category.getTagDescriptions());
        tagDescriptionEntityRepository.saveAll(tagDescriptionEntities);
        return categoryEntityMapper.toDomainModel(categoryEntityRepository.save(categoryEntity));
    }

    @Override
    protected List<Category> enrichList(List<Category> categories) {
        if (categories.isEmpty()) return categories;

        List<UUID> categoryIds = categories.stream().map(Category::getId).toList();
        Map<UUID, List<TagDescription>> tagDescriptionMap =
                tagDescriptionEntityRepository.findByCategoryIdIn(categoryIds).stream()
                        .collect(Collectors.groupingBy(
                                TagDescriptionEntity::getCategoryId,
                                Collectors.mapping(tagDescriptionEntityMapper::toDomainModel, Collectors.toList())));

        categories.forEach(category -> category.enrichTagDescriptions(
                new ArrayList<>(tagDescriptionMap.getOrDefault(category.getId(), Collections.emptyList()))));
        return categories;
    }

    @Override
    public List<Category> getAll() {
        List<CategoryEntity> categoryEntities = categoryEntityRepository.findAll();
        return this.enrichList(categoryEntityMapper.toDomainModelList(categoryEntities));
    }
}
