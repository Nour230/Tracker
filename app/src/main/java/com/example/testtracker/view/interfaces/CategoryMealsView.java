package com.example.testtracker.view.interfaces;

import com.example.testtracker.models.catandcountrymeals.CategoryMeals;
import com.example.testtracker.models.mealdetails.MealDetails;

import java.util.List;

public interface CategoryMealsView {
    public void showCatData(List<MealDetails.MealsDTO> products);
    public void showError(String message);
}
