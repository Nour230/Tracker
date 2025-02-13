package com.example.testtracker.view.interfaces;

import com.example.testtracker.models.catandcountrymeals.CategoryMeals;

import java.util.List;

public interface CategoryMealsView {
    public void showCatData(List<CategoryMeals> products);
    public void showError(String message);
}
