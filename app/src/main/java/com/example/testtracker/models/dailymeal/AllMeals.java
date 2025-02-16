package com.example.testtracker.models.dailymeal;

import com.example.testtracker.models.mealdetails.MealDetails;

import java.util.ArrayList;
import java.util.List;

public class AllMeals {
    List< MealDetails.MealsDTO>meals;
    public List<MealDetails.MealsDTO> getMeals() {
        return meals;
    }
    private AllMeals(){
        meals = new ArrayList<>();
    }
}
