package in.codingage.blooms.controller;

import in.codingage.blooms.dto.CategoryDetail;
import in.codingage.blooms.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
