package in.codingage.blooms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.codingage.blooms.dto.SubCategoryRequest;
import in.codingage.blooms.dto.SubCategoryResponse;
import in.codingage.blooms.service.SubCategoryService;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping
    public void createSubCategory(@RequestBody SubCategoryRequest request) {
        subCategoryService.createSubCategory(request);
    }

    @GetMapping("/{id}")
    public SubCategoryResponse getSubCategoryById(@PathVariable String id) {
        return subCategoryService.getSubCategoryById(id);
    }

    @GetMapping("/all")
    public List<SubCategoryResponse> getSubCategories() {
        return subCategoryService.getAllSubCategories();
    }

    @GetMapping("/category/{categoryId}")
    public List<SubCategoryResponse> getSubCategoriesByCategoryId(@PathVariable String categoryId) {
        return subCategoryService.getSubCategoriesByCategoryId(categoryId);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSubCategory(@PathVariable String id) {
        return subCategoryService.deleteSubCategory(id);
    }

    @PutMapping
    public SubCategoryResponse updateSubCategory(@RequestBody SubCategoryRequest request) {
        return subCategoryService.updateSubCategory(request);
    }
}