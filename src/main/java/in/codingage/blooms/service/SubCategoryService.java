package in.codingage.blooms.service;

import java.util.List;

import in.codingage.blooms.dto.SubCategoryRequest;
import in.codingage.blooms.dto.SubCategoryResponse;

public interface SubCategoryService {
    void createSubCategory(SubCategoryRequest request);

    SubCategoryResponse updateSubCategory(SubCategoryRequest request);

    boolean deleteSubCategory(String id);

    List<SubCategoryResponse> getAllSubCategories();

    SubCategoryResponse getSubCategoryById(String id);

    List<SubCategoryResponse> getSubCategoriesByCategoryId(String categoryId);
}
