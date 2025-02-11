package com.example.testtracker.main_app.home.dailymeal.view;

import com.example.testtracker.main_app.home.dailymeal.model.Meal;
import com.example.testtracker.main_app.mealdetails.model.MealDetails;

import java.util.List;

public interface DailyMealView {
    public void showData(List<Meal> products);
    public void showError(String message);
    void showMealDetails(MealDetails.MealsDTO mealDetails);
}
