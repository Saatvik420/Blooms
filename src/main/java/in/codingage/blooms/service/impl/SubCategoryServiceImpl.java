package in.codingage.blooms.service.impl;

import in.codingage.blooms.dto.SubCategoryRequest;
import in.codingage.blooms.models.Status;
import in.codingage.blooms.models.SubCategory;
import in.codingage.blooms.repository.SubCategoryRepository;
import in.codingage.blooms.service.SubCategoryService;
import in.codingage.blooms.utils.RandomIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Override
    public void createSubCategory(SubCategoryRequest request) {
        // Implementation for creating a subcategory
        SubCategory subCategory = new SubCategory();
        subCategory.setCategoryId(request.getCategoryId());
        subCategory.setName(request.getName());
        subCategory.setDescription(request.getDescription());
        subCategory.setStatus(Status.PUBLISHED.getDisplayName());
        subCategory.setActive(true);
        subCategory.setCreatedDTTM(LocalDateTime.now());
        subCategory.setId(RandomIdUtils.generateRandomId(6));

        //db save
        subCategoryRepository.save(subCategory);


    }
}
