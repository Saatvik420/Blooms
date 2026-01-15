package in.codingage.blooms.dto;

import in.codingage.blooms.models.CategoryMapping;

import java.util.List;

public class BlogRequest {
    private String title;

    private String description;

    private String content;

    private String authorId;

    private List<CategoryMapping> categoryMappings;
}
