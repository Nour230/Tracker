package com.example.testtracker.view.interfaces;

import com.example.testtracker.models.dailymeal.Meal;
import com.example.testtracker.models.mealdetails.MealDetails;

import java.util.List;

public interface DailyMealView {
    default  void showData(List< MealDetails.MealsDTO> products){}
     void addToFav();
    default void addToPlan(){}

    void showError(String message);
    void showMealDetails(MealDetails.MealsDTO mealDetails);
}
