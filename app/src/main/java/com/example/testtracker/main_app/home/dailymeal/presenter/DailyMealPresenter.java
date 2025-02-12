package com.example.testtracker.main_app.home.dailymeal.presenter;

import com.example.testtracker.models.dailymeal.Meal;

public interface DailyMealPresenter {
    public void getProducts();
    public void addToFav(Meal meal);
    public void addToPlan(Meal meal);

}
