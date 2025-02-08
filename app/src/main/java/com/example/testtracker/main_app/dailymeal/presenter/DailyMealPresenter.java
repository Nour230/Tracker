package com.example.testtracker.main_app.dailymeal.presenter;

import com.example.testtracker.main_app.dailymeal.model.Meal;

public interface DailyMealPresenter {
    public void getProducts();
    public void addToFav(Meal meal);
    public void addToPlan(Meal meal);

}
