package in.codingage.blooms;

import in.codingage.blooms.models.Category;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Database instance = null;

    private Database(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database(new ArrayList<>());
        }
        return instance;
    }

    private List<Category> categoryList = new ArrayList<>();

    public List<Category> getCategoryList() {
        return categoryList;
    }

}
