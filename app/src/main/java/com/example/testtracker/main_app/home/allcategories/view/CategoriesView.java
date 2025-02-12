package com.example.testtracker.main_app.home.allcategories.view;

import com.example.testtracker.main_app.home.allcategories.model.Category;

import java.util.List;

public interface CategoriesView {
    public void showCatData(List<Category> products);
    public void showError(String message);
}
