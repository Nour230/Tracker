package com.example.testtracker.view.interfaces;

import com.example.testtracker.models.dailymeal.Meal;
import com.example.testtracker.models.mealdetails.MealDetails;

import java.util.List;

public interface DailyMealView {
    default  void showData(List<Meal> products){}
     void showError(String message);
    void showMealDetails(MealDetails.MealsDTO mealDetails);
}
