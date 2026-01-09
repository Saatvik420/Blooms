package in.codingage.blooms;

import in.codingage.blooms.controller.CategoryController;
import in.codingage.blooms.dto.CategoryRequest;

public class UiClient {
    public static void main(String[] args) {
        System.out.println("UI Client Started");

        CategoryController categoryController = new CategoryController();

        categoryController.createCategory(new CategoryRequest("Technology", "All about technology", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyFSDvZA27CPTHnxlnq8vd-pfw0vcsNGFafA&s"));

        // register - register a new user, we will also have an admin user
        // login -  login existing user
        // admin dashboard - manage categories, sub-categories, blogs, users
        // user dashboard - view blogs, profile, settings, create blogs



    }
}
