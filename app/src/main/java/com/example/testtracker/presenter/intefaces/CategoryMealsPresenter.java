package com.example.testtracker.presenter.intefaces;

import com.example.testtracker.models.db.SavedMeals;

public interface CategoryMealsPresenter {
    public void getMealsByCategory(String category);
    public void getMealsByCountry(String country);
    public void getMealsByIngrediant(String ingrediant);
    public void addToFav(SavedMeals meal);
    public void addToPlan(SavedMeals meal);


}
