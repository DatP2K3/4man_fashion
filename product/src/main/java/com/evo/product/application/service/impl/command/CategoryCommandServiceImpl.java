package com.evo.product.application.service.impl.command;

import com.evo.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.evo.product.application.dto.request.CreateTagDescriptionRequest;
import com.evo.product.application.mapper.CommandMapper;
import com.evo.product.application.service.impl.CategoryCommandService;
import com.evo.product.domain.Category;
import com.evo.product.domain.command.CreateOrUpdateCategoryCmd;
import com.evo.product.domain.repository.CategoryDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CommandMapper commandMapper;
    private final CategoryDomainRepository categoryDomainRepository;

    @Override
    public Category createCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        CreateOrUpdateCategoryCmd createOrUpdateCategoryCmd = commandMapper.from(createOrUpdateCategoryRequest);
        Category category = new Category(createOrUpdateCategoryCmd);
        return categoryDomainRepository.save(category);
    }

    @Override
    public Category updateCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        CreateOrUpdateCategoryCmd createOrUpdateCategoryCmd = commandMapper.from(createOrUpdateCategoryRequest);
        Category category = categoryDomainRepository.getById(createOrUpdateCategoryRequest.getId());
        category.update(createOrUpdateCategoryCmd);
        return categoryDomainRepository.save(category);
    }
}
