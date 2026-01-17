package in.codingage.blooms.service;

import in.codingage.blooms.dto.CategoryDetail;

import java.util.List;

public interface BlogService {
    List<CategoryDetail> getAllCategoriesWithSubCategories();

}
