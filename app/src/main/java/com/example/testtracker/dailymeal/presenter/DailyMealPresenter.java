package com.example.testtracker.dailymeal.presenter;

import com.example.testtracker.dailymeal.model.Meal;

public interface DailyMealPresenter {
    public void getProducts();
    public void addToFav(Meal meal);
    public void addToPlan(Meal meal);

}
