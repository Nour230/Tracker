package com.example.testtracker.presenter.intefaces;

import com.example.testtracker.models.dailymeal.Meal;
import com.example.testtracker.models.db.SavedMeals;

import io.reactivex.rxjava3.core.Completable;

public interface DailyMealPresenter {
    public void getProducts();
    public void addToFav(SavedMeals meal);
    public void addToPlan(SavedMeals meal);

}
