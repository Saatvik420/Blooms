package in.codingage.blooms.service;

import in.codingage.blooms.dto.BlogRequest;
import in.codingage.blooms.dto.BlogResponse;
import in.codingage.blooms.dto.CategoryDetail;

import java.util.List;

public interface BlogService {
    List<CategoryDetail> getAllCategoriesWithSubCategories();

    BlogResponse createBlog(BlogRequest blogRequest);

    List<BlogResponse> getAllBlogs();

    BlogResponse getBlogById(String id);

    BlogResponse updateBlog(String id, BlogRequest blogRequest);

    boolean deleteBlog(String id);

    boolean approveBlog(String id);

    boolean rejectBlog(String id);
}
