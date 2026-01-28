package in.codingage.blooms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryDetail {
    private String subCategoryId;
    private String name;

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
