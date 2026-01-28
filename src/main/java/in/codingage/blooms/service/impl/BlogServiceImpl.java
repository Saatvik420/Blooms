package in.codingage.blooms.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.codingage.blooms.dto.BlogRequest;
import in.codingage.blooms.dto.BlogResponse;
import in.codingage.blooms.dto.CategoryDetail;
import in.codingage.blooms.dto.SubCategoryDetail;
import in.codingage.blooms.models.Blog;
import in.codingage.blooms.models.Category;
import in.codingage.blooms.models.SubCategory;
import in.codingage.blooms.repository.BlogRepository;
import in.codingage.blooms.repository.CategoryRepository;
import in.codingage.blooms.repository.SubCategoryRepository;
import in.codingage.blooms.service.BlogService;
import in.codingage.blooms.utils.RandomIdUtils;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public List<CategoryDetail> getAllCategoriesWithSubCategories() {
        List<CategoryDetail> categoryDetails = new ArrayList<>();
        List<Category> categories = categoryRepository.findAllByActiveTrue();
        categories.forEach(category -> {
            List<SubCategory> subCategories = subCategoryRepository.findAllByCategoryIdAndActiveTrue(category.getId());
            CategoryDetail categoryDetail = new CategoryDetail();
            categoryDetail.setCategoryId(category.getId());
            categoryDetail.setName(category.getName());
            
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

    @Override
    public BlogResponse createBlog(BlogRequest blogRequest) {
        Blog blog = new Blog();
        blog.setId(RandomIdUtils.generateRandomId(10));
        blog.setTitle(blogRequest.getTitle());
        blog.setDescription(blogRequest.getDescription());
        blog.setContent(blogRequest.getContent());
        blog.setAuthorId(blogRequest.getAuthorId());
        blog.setCategoryMappings(blogRequest.getCategoryMappings());
        blog.setStatus("INREVIEW");
        blog.setCreatedDTTM(new Timestamp(System.currentTimeMillis()));

        Blog savedBlog = blogRepository.save(blog);
        return mapToResponse(savedBlog);
    }

    @Override
    public List<BlogResponse> getAllBlogs() {
        return blogRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BlogResponse getBlogById(String id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            return null;
        }
        return mapToResponse(blog);
    }

    @Override
    public BlogResponse updateBlog(String id, BlogRequest blogRequest) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            return null;
        }
        
        if (blogRequest.getTitle() != null) blog.setTitle(blogRequest.getTitle());
        if (blogRequest.getDescription() != null) blog.setDescription(blogRequest.getDescription());
        if (blogRequest.getContent() != null) blog.setContent(blogRequest.getContent());
        if (blogRequest.getCategoryMappings() != null) blog.setCategoryMappings(blogRequest.getCategoryMappings());
        
        Blog updatedBlog = blogRepository.save(blog);
        return mapToResponse(updatedBlog);
    }

    @Override
    public boolean deleteBlog(String id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean approveBlog(String id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog != null) {
            blog.setStatus("PUBLISHED");
            blogRepository.save(blog);
            return true;
        }
        return false;
    }

    @Override
    public boolean rejectBlog(String id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog != null) {
            blog.setStatus("REJECTED");
            blogRepository.save(blog);
            return true;
        }
        return false;
    }

    private BlogResponse mapToResponse(Blog blog) {
        BlogResponse response = new BlogResponse();
        response.setId(blog.getId());
        response.setTitle(blog.getTitle());
        response.setDescription(blog.getDescription());
        response.setContent(blog.getContent());
        response.setStatus(blog.getStatus());
        response.setAuthorId(blog.getAuthorId());
        response.setCreatedDTTM(blog.getCreatedDTTM());
        response.setCategoryMappings(blog.getCategoryMappings());
        return response;
    }
}
