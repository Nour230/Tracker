package com.example.testtracker.db;

import androidx.lifecycle.LiveData;

import com.example.testtracker.dailymeal.model.Meal;

import java.util.List;

public interface MealLocalDataSource {
    LiveData<List<Meal>> getAllMeals();
    void insertMeal(Meal meal);
    void deleteMeal(Meal meal);

}
