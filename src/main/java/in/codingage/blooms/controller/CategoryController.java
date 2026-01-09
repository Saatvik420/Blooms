package in.codingage.blooms.controller;

import ch.qos.logback.core.testUtil.RandomUtil;
import in.codingage.blooms.Database;
import in.codingage.blooms.dto.CategoryRequest;
import in.codingage.blooms.dto.CategoryResponse;
import in.codingage.blooms.models.Category;
import in.codingage.blooms.models.Status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;

public class CategoryController {

    // CRUD - Create, Read, Update , Delete

//    Supplier<Integer> capacitySupplier = () -> (int) (Math.random() * 10) + 1;

    public void createCategory(CategoryRequest categoryRequest){
        Category category = new Category();
        // coming from ui request
        category.setName(categoryRequest.getTitle());
        category.setDescription(categoryRequest.getDesc());
        category.setImageUrl(categoryRequest.getcUrl());

        // for now lets' give this access only to admins
        category.setStatus(Status.PUBLISHED.getDisplayName());
        category.setId(String.valueOf(System.currentTimeMillis()));

        category.setCreatedBy("ADMIN");

        category.setActive(true);

        category.setCreatedDTTM(LocalDateTime.now());

        Database database = Database.getInstance();
        database.getCategoryList().add(category);
    }

    public CategoryResponse getCategory(String categoryId){
        List<Category> categoryList = Database.getInstance().getCategoryList();
        for(Category category : categoryList){
            if(category.getId().equals(categoryId)){
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setcUrl(category.getImageUrl());
                categoryResponse.setId(category.getId());
                categoryResponse.setTitle(category.getName());
                categoryResponse.setDesc(category.getDescription());
                return categoryResponse;
            }
        }
        return null;
    }

    public List<CategoryResponse> getCategories(){
        List<Category> categoryList = Database.getInstance().getCategoryList();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for(Category category : categoryList) {
            if (category.isActive()) {
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setcUrl(category.getImageUrl());
                categoryResponse.setId(category.getId());
                categoryResponse.setTitle(category.getName());
                categoryResponse.setDesc(category.getDescription());
                categoryResponses.add(categoryResponse);
            }
        }
        return categoryResponses;
        }

     public boolean deleteCategory(String categoryId){
        // iterate the list that comes from your database and set the active falg to false
         // return true
         // if id not found, return false

         return true;
     }

     public CategoryResponse updateCategory(CategoryRequest categoryRequest){
        // fetch ctegory by id and update its' name, desc and curl using category request
         // make sure you are updating the found category and the list..
         // return updated category
         return null;
     }
    }




