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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.codingage.blooms.dto.CategoryRequest;
import in.codingage.blooms.dto.CategoryResponse;
import in.codingage.blooms.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public void createCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.createCategory(categoryRequest);
    }

    @GetMapping
    public CategoryResponse getCategory(@RequestParam String categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse getCategoryByPathParam(@PathVariable(value = "categoryId") String categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("/all")
    public List<CategoryResponse> getCategories() {
        return categoryService.getAllCategories();
    }

    @DeleteMapping
    public boolean deleteCategory(String categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @PutMapping
    public CategoryResponse updateCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(categoryRequest);
    }
}