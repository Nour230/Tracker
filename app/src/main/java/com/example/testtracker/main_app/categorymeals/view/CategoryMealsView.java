package com.example.testtracker.main_app.categorymeals.view;

import com.example.testtracker.main_app.categorymeals.model.CategoryMeals;

import java.util.List;

public interface CategoryMealsView {
    public void showCatData(List<CategoryMeals> products);
    public void showError(String message);
}
