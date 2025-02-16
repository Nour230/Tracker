package com.example.testtracker.presenter.intefaces;

import com.example.testtracker.models.db.SavedMeals;

import java.util.List;

public interface FavPresenter {
    public void getFavMeals();
    public void deletFromFav(SavedMeals meal);
}
