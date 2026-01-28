package in.codingage.blooms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.codingage.blooms.dto.CategoryRequest;
import in.codingage.blooms.dto.CategoryResponse;
import in.codingage.blooms.dto.SubCategoryRequest;
import in.codingage.blooms.dto.SubCategoryResponse;
import in.codingage.blooms.models.User;
import in.codingage.blooms.service.BlogService;
import in.codingage.blooms.service.CategoryService;
import in.codingage.blooms.service.SubCategoryService;
import in.codingage.blooms.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    // Category Management
    @PostMapping("/categories")
    public void createCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.createCategory(categoryRequest);
    }

    @PutMapping("/categories")
    public CategoryResponse updateCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(categoryRequest);
    }

    @DeleteMapping("/categories/{id}")
    public boolean deleteCategory(@PathVariable String id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // SubCategory Management
    @PostMapping("/subcategories")
    public void createSubCategory(@RequestBody SubCategoryRequest request) {
        subCategoryService.createSubCategory(request);
    }

    @PutMapping("/subcategories")
    public SubCategoryResponse updateSubCategory(@RequestBody SubCategoryRequest request) {
        return subCategoryService.updateSubCategory(request);
    }

    @DeleteMapping("/subcategories/{id}")
    public boolean deleteSubCategory(@PathVariable String id) {
        return subCategoryService.deleteSubCategory(id);
    }

    @GetMapping("/subcategories")
    public List<SubCategoryResponse> getAllSubCategories() {
        return subCategoryService.getAllSubCategories();
    }

    // Blog Management
    @GetMapping("/blogs")
    public List<in.codingage.blooms.dto.BlogResponse> getAllBlogs() {
        return blogService.getAllBlogs();
    }


    @PutMapping("/blogs/{id}/approve")
    public boolean approveBlog(@PathVariable String id) {
        return blogService.approveBlog(id);
    }

    @PutMapping("/blogs/{id}/reject")
    public boolean rejectBlog(@PathVariable String id) {
        return blogService.rejectBlog(id);
    }

    @DeleteMapping("/blogs/{id}")
    public boolean deleteBlog(@PathVariable String id) {
        return blogService.deleteBlog(id);
    }

    // User Management
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public boolean deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    // Dashboard Statistics
    @GetMapping("/dashboard/stats")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        List<CategoryResponse> categories = categoryService.getAllCategories();
        List<SubCategoryResponse> subCategories = subCategoryService.getAllSubCategories();
        List<in.codingage.blooms.dto.BlogResponse> blogs = blogService.getAllBlogs();
        List<User> users = userService.getAllUsers();

        long publishedBlogs = blogs.stream().filter(b -> "Published".equalsIgnoreCase(b.getStatus())).count();
        long pendingBlogs = blogs.stream().filter(b -> "In Review".equalsIgnoreCase(b.getStatus())).count();
        long rejectedBlogs = blogs.stream().filter(b -> "Rejected".equalsIgnoreCase(b.getStatus())).count();

        stats.put("totalCategories", categories.size());
        stats.put("totalSubCategories", subCategories.size());
        stats.put("totalBlogs", blogs.size());
        stats.put("publishedBlogs", publishedBlogs);
        stats.put("pendingBlogs", pendingBlogs);
        stats.put("rejectedBlogs", rejectedBlogs);
        stats.put("totalUsers", users.size());

        return stats;
    }
}