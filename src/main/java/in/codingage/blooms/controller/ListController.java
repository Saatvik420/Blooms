package in.codingage.blooms.controller;

import in.codingage.blooms.dto.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

public class ListController {
    public static void main(String[] args) {
        // string data type ke elements ko add kar sakte ho
        // JCF - Java Collection Framework
        List<String> items = new ArrayList<>();

        // ordered collection of elements
        items.add("Vishal"); // 0
        items.add("Pintu"); // 0
        items.add("Abhishek");
        items.add("Abhishke K");
        items.add("Saurabh");
        items.add("Sunidhi");
        items.add("Surbhi");  // 5

        for (String abc : items) {
            System.out.println(abc);
        }

        // method overloading
        items.remove(0);

        // IOUB
        // Exception Handling

        try {
            System.out.println(items.get(6));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds exception caught: " + e.getMessage());
        }

        for (String abc : items) {
            System.out.println(abc);
        }

        List<CategoryResponse> categoryResponses = new ArrayList<>();

        categoryResponses.add(new CategoryResponse("1", "Technology A", "All about technology", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyFSDvZA27CPTHnxlnq8vd-pfw0vcsNGFafA&s"));
        categoryResponses.add(new CategoryResponse("2", "Technology B", "All about technology", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyFSDvZA27CPTHnxlnq8vd-pfw0vcsNGFafA&s"));
        categoryResponses.add(new CategoryResponse("3", "Technology C", "All about technology", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyFSDvZA27CPTHnxlnq8vd-pfw0vcsNGFafA&s"));

        for (CategoryResponse cat : categoryResponses) {
            System.out.println("Category ID: " + cat.getId() + ", Name: " + cat.getTitle());
        }

        String idToFind = "20";

        for (CategoryResponse cat : categoryResponses) {
            if (cat.getId().equals(idToFind)) {
                System.out.println("Found Category with ID " + idToFind + ": " + cat.getTitle());
            }
        }

        String idToRemove = "2";

        for (CategoryResponse cat : categoryResponses) {
            if (cat.getId().equals(idToRemove)) {
                categoryResponses.remove(cat);
                break;
            }
        }

        for (CategoryResponse cat : categoryResponses) {
            System.out.println("Category ID: " + cat.getId() + ", Name: " + cat.getTitle());
        }
    }
}