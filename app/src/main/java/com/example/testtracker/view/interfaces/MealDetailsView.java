package com.example.testtracker.view.interfaces;

import com.example.testtracker.models.mealdetails.MealDetails;

public interface MealDetailsView {
    void showMealDetails(MealDetails.MealsDTO meal);
    void addToFav();
    void addToPlan();

    void showError(String message);
}
