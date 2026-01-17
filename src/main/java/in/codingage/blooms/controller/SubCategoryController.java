package in.codingage.blooms.controller;

import in.codingage.blooms.dto.SubCategoryRequest;
import in.codingage.blooms.dto.SubCategoryResponse;
import in.codingage.blooms.models.SubCategory;
import in.codingage.blooms.repository.SubCategoryRepository;
import in.codingage.blooms.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {
    //// TODO: 09/01/26 complete crud here
    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping
    // create subcategory
    public void createSubCategory(SubCategoryRequest request) {
        subCategoryService.createSubCategory(request);
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
