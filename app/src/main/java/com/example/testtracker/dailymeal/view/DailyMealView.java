package com.example.testtracker.dailymeal.view;

import com.example.testtracker.dailymeal.model.Meal;

import java.util.List;

public interface DailyMealView {
    public void showData(List<Meal> products);
    public void showError(String message);
}
