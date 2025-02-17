package com.example.testtracker.view.interfaces;

import com.example.testtracker.models.dailymeal.Meal;
import com.example.testtracker.models.mealdetails.MealDetails;

import java.util.List;

public interface DailyMealView {
    default  void showData(List< MealDetails.MealsDTO> products){}
     default void addToFav(){}
    default void addToPlan(){}

   default void showError(String message){}
    default void showLoading() {}
    default void hideLoading() {}
    default void showMealDetails(MealDetails.MealsDTO mealDetails){}
}
