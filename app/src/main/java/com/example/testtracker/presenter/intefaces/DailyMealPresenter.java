package com.example.testtracker.presenter.intefaces;

import com.example.testtracker.models.dailymeal.Meal;

public interface DailyMealPresenter {
    public void getProducts();
    public void addToFav(Meal meal);
    public void addToPlan(Meal meal);

}
