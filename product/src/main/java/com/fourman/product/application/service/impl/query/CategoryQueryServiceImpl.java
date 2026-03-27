package com.fourman.product.application.service.impl.query;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fourman.common.exception.ResponseException;
import com.fourman.product.application.dto.mapper.CategoryDTOMapper;
import com.fourman.product.application.dto.response.CategoryDTO;
import com.fourman.product.application.service.CategoryQueryService;
import com.fourman.product.domain.Category;
import com.fourman.product.domain.repository.CategoryDomainRepository;
import com.fourman.product.infrastructure.persistence.entity.CategoryEntity;
import com.fourman.product.infrastructure.persistence.repository.CategoryEntityRepository;
import com.fourman.product.infrastructure.support.exception.NotFoundError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryDTOMapper categoryDTOMapper;
    private final CategoryDomainRepository categoryDomainRepository;
    private final CategoryEntityRepository categoryEntityRepository;

    @Override
    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryDomainRepository.getAll();
        return categoryDTOMapper.domainModelsToDTOs(categories);
    }

    @Override
    public List<CategoryDTO> getCategoriesByProductType(String productType) {
        List<CategoryEntity> categories = categoryEntityRepository.findByProductType(productType);
        return categoryDTOMapper.entitiesToDTOs(categories);
    }

    @Override
    public CategoryDTO getCategoryById(UUID id) {
        CategoryEntity categoryEntity = categoryEntityRepository
                .findById(id)
                .orElseThrow(() -> new ResponseException(NotFoundError.CATEGORY_NOT_FOUND));
        return categoryDTOMapper.entityToDTO(categoryEntity);
    }
}
