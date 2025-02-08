package com.example.testtracker.main_app.dailymeal.view;

import com.example.testtracker.main_app.dailymeal.model.Meal;

import java.util.List;

public interface DailyMealView {
    public void showData(List<Meal> products);
    public void showError(String message);
}
