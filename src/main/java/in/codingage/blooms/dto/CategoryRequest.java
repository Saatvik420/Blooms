package in.codingage.blooms.dto;

public class CategoryRequest {
    private String title;
    private String id;
    private String desc;
    private String cUrl;

    public CategoryRequest(String title, String desc, String cUrl) {
        this.title = title;
        this.desc = desc;
        this.cUrl = cUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getcUrl() {
        return cUrl;
    }

    public void setcUrl(String cUrl) {
        this.cUrl = cUrl;
    }
}
