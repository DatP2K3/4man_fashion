package com.fourman.product.application.service.impl.command;

import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.product.application.dto.mapper.CategoryDTOMapper;
import com.fourman.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.fourman.product.application.dto.response.CategoryDTO;
import com.fourman.product.application.mapper.CommandMapper;
import com.fourman.product.application.service.CategoryCommandService;
import com.fourman.product.domain.Category;
import com.fourman.product.domain.command.CreateOrUpdateCategoryCmd;
import com.fourman.product.domain.repository.CategoryDomainRepository;

import lombok.RequiredArgsConstructor;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CommandMapper commandMapper;
    private final CategoryDomainRepository categoryDomainRepository;
    private final CategoryDTOMapper categoryDTOMapper;

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public CategoryDTO createCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        CreateOrUpdateCategoryCmd createOrUpdateCategoryCmd = commandMapper.from(createOrUpdateCategoryRequest);
        Category category = new Category(createOrUpdateCategoryCmd);
        return categoryDTOMapper.domainModelToDTO(categoryDomainRepository.save(category));
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public CategoryDTO updateCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        CreateOrUpdateCategoryCmd createOrUpdateCategoryCmd = commandMapper.from(createOrUpdateCategoryRequest);
        Category category = categoryDomainRepository.getById(createOrUpdateCategoryRequest.getId());
        category.update(createOrUpdateCategoryCmd);
        return categoryDTOMapper.domainModelToDTO(categoryDomainRepository.save(category));
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void visibilityCategory(UUID id) {
        Category category = categoryDomainRepository.getById(id);
        category.toggleVisibility();
        categoryDomainRepository.save(category);
    }
}
