package in.codingage.blooms.controller;

import in.codingage.blooms.dto.BlogRequest;
import in.codingage.blooms.dto.BlogResponse;
import in.codingage.blooms.dto.CategoryDetail;
import in.codingage.blooms.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/categories")
    public List<CategoryDetail> getAllCategoriesWithSubCategories() {
        return blogService.getAllCategoriesWithSubCategories();
    }

    @PostMapping
    public BlogResponse createBlog(@RequestBody BlogRequest blogRequest) {
        return blogService.createBlog(blogRequest);
    }

    @GetMapping
    public List<BlogResponse> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/{id}")
    public BlogResponse getBlogById(@PathVariable String id) {
        return blogService.getBlogById(id);
    }

    @PutMapping("/{id}")
    public BlogResponse updateBlog(@PathVariable String id, @RequestBody BlogRequest blogRequest) {
        return blogService.updateBlog(id, blogRequest);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBlog(@PathVariable String id) {
        return blogService.deleteBlog(id);
    }
}