package in.codingage.blooms.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.codingage.blooms.dto.SubCategoryRequest;
import in.codingage.blooms.dto.SubCategoryResponse;
import in.codingage.blooms.models.Status;
import in.codingage.blooms.models.SubCategory;
import in.codingage.blooms.repository.SubCategoryRepository;
import in.codingage.blooms.service.SubCategoryService;
import in.codingage.blooms.utils.RandomIdUtils;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Override
    public void createSubCategory(SubCategoryRequest request) {
        // Implementation for creating a subcategory
        SubCategory subCategory = new SubCategory();
        subCategory.setCategoryId(request.getCategoryId());
        subCategory.setName(request.getName());
        subCategory.setDescription(request.getDescription());
        subCategory.setStatus(Status.PUBLISHED.getDisplayName());
        subCategory.setActive(true);
        subCategory.setCreatedDTTM(LocalDateTime.now());
        subCategory.setId(RandomIdUtils.generateRandomId(6));

        //db save
        subCategoryRepository.save(subCategory);
    }

    @Override
    public SubCategoryResponse updateSubCategory(SubCategoryRequest request) {
        if (request.getId() == null) {
            return null;
        }
        SubCategory subCategory = subCategoryRepository.findById(request.getId()).orElse(null);
        if (subCategory == null) {
            return null;
        }
        
        if (request.getName() != null) subCategory.setName(request.getName());
        if (request.getDescription() != null) subCategory.setDescription(request.getDescription());
        if (request.getCategoryId() != null) subCategory.setCategoryId(request.getCategoryId());
        
        SubCategory updatedSubCategory = subCategoryRepository.save(subCategory);
        return mapToResponse(updatedSubCategory);
    }

    @Override
    public boolean deleteSubCategory(String id) {
        if (subCategoryRepository.existsById(id)) {
            subCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<SubCategoryResponse> getAllSubCategories() {
        return subCategoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SubCategoryResponse getSubCategoryById(String id) {
        SubCategory subCategory = subCategoryRepository.findById(id).orElse(null);
        if (subCategory == null) {
            return null;
        }
        return mapToResponse(subCategory);
    }

    @Override
    public List<SubCategoryResponse> getSubCategoriesByCategoryId(String categoryId) {
        return subCategoryRepository.findAllByCategoryIdAndActiveTrue(categoryId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private SubCategoryResponse mapToResponse(SubCategory subCategory) {
        SubCategoryResponse response = new SubCategoryResponse();
        response.setId(subCategory.getId());
        response.setCategoryId(subCategory.getCategoryId());
        response.setName(subCategory.getName());
        response.setDescription(subCategory.getDescription());
        response.setActive(subCategory.isActive());
        response.setStatus(subCategory.getStatus());
        response.setCreatedBy(subCategory.getCreatedBy());
        response.setCreatedDTTM(subCategory.getCreatedDTTM());
        return response;
    }
}
