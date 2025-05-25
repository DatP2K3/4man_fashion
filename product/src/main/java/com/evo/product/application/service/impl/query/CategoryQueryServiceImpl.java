package com.evo.product.application.service.impl.query;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.evo.product.application.dto.mapper.CategoryDTOMapper;
import com.evo.product.application.dto.response.CategoryDTO;
import com.evo.product.application.service.CategoryQueryService;
import com.evo.product.domain.Category;
import com.evo.product.domain.repository.CategoryDomainRepository;
import com.evo.product.infrastructure.persistence.entity.CategoryEntity;
import com.evo.product.infrastructure.persistence.repository.CategoryEntityRepository;
import com.evo.product.infrastructure.support.exception.AppErrorCode;
import com.evo.product.infrastructure.support.exception.AppException;

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
                .orElseThrow(() -> new AppException(AppErrorCode.CATEGORY_NOT_FOUND));
        return categoryDTOMapper.entityToDTO(categoryEntity);
    }
}
