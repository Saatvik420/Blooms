package in.codingage.blooms.controller;

import in.codingage.blooms.Database;
import in.codingage.blooms.dto.CategoryRequest;
import in.codingage.blooms.dto.CategoryResponse;
import in.codingage.blooms.models.Category;
import in.codingage.blooms.models.Status;
import in.codingage.blooms.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    // CRUD - Create, Read, Update , Delete

//    Supplier<Integer> capacitySupplier = () -> (int) (Math.random() * 10) + 1;

    @Autowired
    private CategoryRepository categoryRepository;


    //POST
    @PostMapping()
    public void createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = new Category();
        // coming from ui request
        category.setName(categoryRequest.getTitle());
        category.setDescription(categoryRequest.getDesc());
        category.setImageUrl(categoryRequest.getCategoryUrl());

        // for now lets' give this access only to admins
        category.setStatus(Status.PUBLISHED.getDisplayName());
        category.setId(String.valueOf(System.currentTimeMillis()));

        category.setCreatedBy("ADMIN");

        category.setActive(true);

        category.setCreatedDTTM(LocalDateTime.now());

        // local db - item save
        Database database = Database.getInstance();

        // persist category object to database
        categoryRepository.save(category);


        database.getCategoryList().add(category);
    }


    @GetMapping
    public CategoryResponse getCategory(@RequestParam String categoryId) {
//        List<Category> categoryList = categoryRepository.findAll();
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryUrl(category.get().getImageUrl());
            categoryResponse.setId(category.get().getId());
            categoryResponse.setTitle(category.get().getName());
            categoryResponse.setDesc(category.get().getDescription());
            return categoryResponse;
        }
        return null;
    }


    @GetMapping("/{categoryId}")
    public CategoryResponse getCategoryByPathParam(@PathVariable(value = "categoryId") String categoryId) {
//        List<Category> categoryList = Database.getInstance().getCategoryList();
//        for(Category category : categoryList){
//            if(category.getId().equals(categoryId)){
//                CategoryResponse categoryResponse = new CategoryResponse();
//                categoryResponse.setCategoryUrl(category.getImageUrl());
//                categoryResponse.setId(category.getId());
//                categoryResponse.setTitle(category.getName());
//                categoryResponse.setDesc(category.getDescription());
//                return categoryResponse;
//            }
//        }
//        return null;

        return getCategory(categoryId);
    }


    // GET
    @GetMapping("/all")
    public List<CategoryResponse> getCategories() {
//        List<Category> categoryList = Database.getInstance().getCategoryList();
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.isActive()) {
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setCategoryUrl(category.getImageUrl());
                categoryResponse.setId(category.getId());
                categoryResponse.setTitle(category.getName());
                categoryResponse.setDesc(category.getDescription());
                categoryResponses.add(categoryResponse);
            }
        }
        return categoryResponses;
    }

    // DELETE
    @DeleteMapping
    public boolean deleteCategory(String categoryId) {
        // iterate the list that comes from your database and set the active flag to false
        // return true
        // if id not found, return false

//         List<Category> categoryList = Database.getInstance().getCategoryList();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setActive(false);
            categoryRepository.save(category);
            return true;
        }
        return false;

    }

    // UPDATE - PUT
    @PutMapping
    public CategoryResponse updateCategory(CategoryRequest categoryRequest) {
        // fetch category by id and update its name, desc and curl using category request
        // validation to return from here only if id is not present
        CategoryResponse categoryResponse = new CategoryResponse();
        if (categoryRequest.getId() == null) {
            // we will send error to UI later
            return categoryResponse;
        }
        Optional<Category> categoryOptional = categoryRepository.findById(categoryRequest.getId());
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();

            category.setName(categoryRequest.getTitle());
            category.setDescription(categoryRequest.getDesc());
            category.setImageUrl(categoryRequest.getCategoryUrl());
            // create a category response object
            categoryResponse.setDesc(category.getDescription());
            categoryResponse.setId(category.getId());
            categoryResponse.setCategoryUrl(category.getImageUrl());
            categoryResponse.setTitle(category.getName());
            return categoryResponse;
        }
        return categoryResponse;
    }
}




