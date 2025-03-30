package com.evo.product.infrastructure.domainrepository;

import com.evo.common.repository.AbstractDomainRepository;
import com.evo.product.domain.Category;
import com.evo.product.domain.TagDescription;
import com.evo.product.domain.repository.CategoryDomainRepository;
import com.evo.product.infrastructure.persistence.entity.CategoryEntity;
import com.evo.product.infrastructure.persistence.entity.TagDescriptionEntity;
import com.evo.product.infrastructure.persistence.mapper.CategoryEntityMapper;
import com.evo.product.infrastructure.persistence.mapper.TagDescriptionEntityMapper;
import com.evo.product.infrastructure.persistence.repository.CategoryEntityRepository;
import com.evo.product.infrastructure.persistence.repository.TagDescriptionEntityRepository;
import com.evo.product.infrastructure.support.exception.AppErrorCode;
import com.evo.product.infrastructure.support.exception.AppException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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
        CategoryEntity categoryEntity = categoryEntityRepository.findById(uuid).orElseThrow(() -> new AppException(AppErrorCode.CATEGORY_NOT_FOUND));
        return enrich(categoryEntityMapper.toDomainModel(categoryEntity));
    }

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);
        List<TagDescriptionEntity> tagDescriptionEntities = tagDescriptionEntityMapper.toEntityList(category.getTagDescriptions());
        tagDescriptionEntityRepository.saveAll(tagDescriptionEntities);
        return categoryEntityMapper.toDomainModel(categoryEntityRepository.save(categoryEntity));
    }

    @Override
    protected List<Category> enrichList(List<Category> categories) {
        if (categories.isEmpty()) return categories;

        List<UUID> categoryIds = categories.stream().map(Category::getId).toList();
        Map<UUID, List<TagDescription>> tagDescriptionMap =
                tagDescriptionEntityRepository.findByCategoryIdInAndDeletedFalse(categoryIds).stream()
                        .collect(Collectors.groupingBy(
                                TagDescriptionEntity::getCategoryId,
                                Collectors.mapping(tagDescriptionEntityMapper::toDomainModel, Collectors.toList())));

        categories.forEach(category -> category.setTagDescriptions(
                new ArrayList<>(tagDescriptionMap.getOrDefault(category.getId(), Collections.emptyList()))));
        return categories;
    }
}
