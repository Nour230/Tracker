package com.example.testtracker.presenter.catandcountrymeals;

import android.util.Log;

import com.example.testtracker.models.catandcountrymeals.CategoryMeals;
import com.example.testtracker.models.catandcountrymeals.CategoryMealsReposetoryImpl;
import com.example.testtracker.presenter.intefaces.CategoryMealsPresenter;
import com.example.testtracker.view.interfaces.CategoryMealsView;
import com.example.testtracker.network.NetworkCallBack;

import java.util.List;

public class CategoryMealsPresenterImpl implements NetworkCallBack, CategoryMealsPresenter {
    private static final String TAG = "MainActivity";
    private final CategoryMealsView view;
    private final CategoryMealsReposetoryImpl repo;

    public CategoryMealsPresenterImpl(CategoryMealsView view, CategoryMealsReposetoryImpl repo) {
        this.view = view;
        this.repo = repo;
    }
    @Override
    public void getMealsByCategory(String category) {
        Log.d(TAG, "Fetching meals for category: " + category);
        repo.getAllCategoriesMeals(category,this);
    }

    @Override
    public void getMealsByCountry(String country) {
        Log.d(TAG, "Fetching meals for country: " + country);
        repo.getAllCountriesMeals(country,this);
    }

    @Override
    public void onCategoryMealsSuccess(List<CategoryMeals> meal) {
        view.showCatData(meal);
    }

    @Override
    public void onCountryMealsSuccess(List<CategoryMeals> meal) {
        Log.i(TAG, "onCountryMealsSuccess: ");
        view.showCatData(meal);
    }

    @Override
    public void onFailure(String message) {
        view.showError(message);
    }
}
