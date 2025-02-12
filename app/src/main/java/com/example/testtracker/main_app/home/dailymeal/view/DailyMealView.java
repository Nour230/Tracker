package com.example.testtracker.main_app.home.dailymeal.view;

import com.example.testtracker.models.dailymeal.Meal;
import com.example.testtracker.models.mealdetails.MealDetails;

import java.util.List;

public interface DailyMealView {
    default public void showData(List<Meal> products){}
    public void showError(String message);
    void showMealDetails(MealDetails.MealsDTO mealDetails);
}
