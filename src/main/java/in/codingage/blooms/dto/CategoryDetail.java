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

}
