package in.codingage.blooms.controller;

import in.codingage.blooms.dto.SubCategoryRequest;
import in.codingage.blooms.dto.SubCategoryResponse;
import in.codingage.blooms.models.SubCategory;
import in.codingage.blooms.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SubCategoryController {
    //// TODO: 09/01/26 complete crud here
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    // create subcategory
    public void createSubCategory(SubCategoryRequest request) {
        // implementation here
    }

    // read subcategory
    public SubCategoryResponse getSubCategoryById(String id) {
        // implementation here
        return null;
    }

    // read subcategories
    public List<SubCategoryResponse> getSubCategories() {
        // implementation here
        return null;
    }

    // delete subcategory
    public void deleteSubCategory(String id) {
        // implementation here
    }

    // update subcategory
    public void updateSubCategory(SubCategoryRequest request) {
        // implementation here
    }


}
