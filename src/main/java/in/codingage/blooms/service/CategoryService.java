package in.codingage.blooms.service;

import in.codingage.blooms.dto.CategoryRequest;
import in.codingage.blooms.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void createCategory(CategoryRequest categoryRequest);

    CategoryResponse getCategoryById(String categoryId);

    List<CategoryResponse> getAllCategories();

    boolean deleteCategory(String categoryId);

    CategoryResponse updateCategory(CategoryRequest categoryRequest);
}
