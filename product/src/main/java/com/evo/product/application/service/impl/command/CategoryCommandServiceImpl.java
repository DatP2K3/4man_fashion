package com.evo.product.application.service.impl.command;

import java.util.List;

import org.springframework.stereotype.Service;

import com.evo.product.application.dto.mapper.CategoryDTOMapper;
import com.evo.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.evo.product.application.dto.response.CategoryDTO;
import com.evo.product.application.mapper.CommandMapper;
import com.evo.product.application.service.CategoryCommandService;
import com.evo.product.domain.Category;
import com.evo.product.domain.command.CreateOrUpdateCategoryCmd;
import com.evo.product.domain.repository.CategoryDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CommandMapper commandMapper;
    private final CategoryDomainRepository categoryDomainRepository;
    private final CategoryDTOMapper categoryDTOMapper;

    @Override
    public CategoryDTO createCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        CreateOrUpdateCategoryCmd createOrUpdateCategoryCmd = commandMapper.from(createOrUpdateCategoryRequest);
        Category category = new Category(createOrUpdateCategoryCmd);
        return categoryDTOMapper.domainModelToDTO(categoryDomainRepository.save(category));
    }

    @Override
    public CategoryDTO updateCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        CreateOrUpdateCategoryCmd createOrUpdateCategoryCmd = commandMapper.from(createOrUpdateCategoryRequest);
        Category category = categoryDomainRepository.getById(createOrUpdateCategoryRequest.getId());
        category.update(createOrUpdateCategoryCmd);
        return categoryDTOMapper.domainModelToDTO(categoryDomainRepository.save(category));
    }

    @Override
    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryDomainRepository.getAll();
        return categoryDTOMapper.domainModelsToDTOs(categories);
    }
}
