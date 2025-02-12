package com.example.testtracker.models.allcategory;

import java.util.ArrayList;
import java.util.List;

public class AllCategories {
    private final List<Category> categories;

    private AllCategories() {
        categories = new ArrayList<>();
    }

    public List<Category> getCategories() {
        return categories;
    }

}
