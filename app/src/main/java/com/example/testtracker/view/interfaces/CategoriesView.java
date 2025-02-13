package com.example.testtracker.view.interfaces;

import com.example.testtracker.models.allcategory.Category;

import java.util.List;

public interface CategoriesView {
    public void showCatData(List<Category> products);
    public void showError(String message);
}
