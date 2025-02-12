package com.example.testtracker.main_app.home.dailymeal.view;

import android.view.View;

import com.example.testtracker.main_app.home.dailymeal.model.Meal;
import com.example.testtracker.main_app.mealdetails.model.MealDetails;

public interface OnMealClickListener {
    void onMealClick(String mealId, View view);
}
