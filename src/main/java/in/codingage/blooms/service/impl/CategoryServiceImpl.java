package in.codingage.blooms.service.impl;

import in.codingage.blooms.dto.CategoryRequest;
import in.codingage.blooms.dto.CategoryResponse;
import in.codingage.blooms.models.Category;
import in.codingage.blooms.repository.CategoryRepository;
import in.codingage.blooms.service.CategoryService;
import in.codingage.blooms.utils.RandomIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setId(RandomIdUtils.generateRandomId(10));
        category.setName(categoryRequest.getTitle());
        category.setDescription(categoryRequest.getDesc());
        category.setImageUrl(categoryRequest.getCategoryUrl());
        category.setActive(true);
        category.setStatus("ACTIVE");
        category.setCreatedDTTM(LocalDateTime.now());
        
        categoryRepository.save(category);
    }

    @Override
    public CategoryResponse getCategoryById(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            return null;
        }
        return mapToResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteCategory(String categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
            return true;
        }
        return false;
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest) {
        if (categoryRequest.getId() == null) {
            return null;
        }
        Category category = categoryRepository.findById(categoryRequest.getId()).orElse(null);
        if (category == null) {
            return null;
        }

        if (categoryRequest.getTitle() != null) category.setName(categoryRequest.getTitle());
        if (categoryRequest.getDesc() != null) category.setDescription(categoryRequest.getDesc());
        if (categoryRequest.getCategoryUrl() != null) category.setImageUrl(categoryRequest.getCategoryUrl());
        
        Category updatedCategory = categoryRepository.save(category);
        return mapToResponse(updatedCategory);
    }

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setTitle(category.getName());
        response.setDesc(category.getDescription());
        response.setCategoryUrl(category.getImageUrl());
        return response;
    }
}
