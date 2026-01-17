package in.codingage.blooms.service.impl;

import in.codingage.blooms.dto.CategoryDetail;
import in.codingage.blooms.dto.SubCategoryDetail;
import in.codingage.blooms.models.Category;
import in.codingage.blooms.models.SubCategory;
import in.codingage.blooms.repository.CategoryRepository;
import in.codingage.blooms.repository.SubCategoryRepository;
import in.codingage.blooms.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public List<CategoryDetail> getAllCategoriesWithSubCategories() {
        List<CategoryDetail> categoryDetails = new ArrayList<>();
        List<Category> categories = categoryRepository.findAllByActiveTrue();
        categories.forEach(category -> {
            List<SubCategory> subCategories = subCategoryRepository.findAllByCategoryIdAndActiveTrue(category.getId());
            // You can map these subCategories to your CategoryDetail DTO as needed
            CategoryDetail categoryDetail = new CategoryDetail();
            categoryDetail.setCategoryId(category.getId());
            categoryDetail.setName(category.getName());
            // Assuming you have a method to set sub-categories
            List<SubCategoryDetail> subCategoryDetails = new ArrayList<>();
            subCategories.forEach(subCategory -> {
                SubCategoryDetail detail = new SubCategoryDetail();
                detail.setSubCategoryId(subCategory.getId());
                detail.setName(subCategory.getName());
                subCategoryDetails.add(detail);
            });
            categoryDetail.setSubCategoryDetailList(subCategoryDetails);
            categoryDetails.add(categoryDetail);
        });

        return categoryDetails;
    }
}
