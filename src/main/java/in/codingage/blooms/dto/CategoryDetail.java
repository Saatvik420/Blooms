package in.codingage.blooms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CategoryDetail {
    private String categoryId;
    private String name;
    private List<SubCategoryDetail> subCategoryDetailList;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubCategoryDetail> getSubCategoryDetailList() {
        return subCategoryDetailList;
    }

    public void setSubCategoryDetailList(List<SubCategoryDetail> subCategoryDetailList) {
        this.subCategoryDetailList = subCategoryDetailList;
    }
}
