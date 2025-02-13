package com.example.testtracker.view.interfaces;

import com.example.testtracker.models.mealdetails.MealDetails;

public interface MealDetailsView {
    void showMealDetails(MealDetails.MealsDTO meal);
    void showError(String message);
}
