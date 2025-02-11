package com.example.testtracker.main_app.mealdetails.view;

import com.example.testtracker.main_app.mealdetails.model.MealDetails;

public interface MealDetailsView {
    void showMealDetails(MealDetails.MealsDTO meal);
    void showError(String message);
}
