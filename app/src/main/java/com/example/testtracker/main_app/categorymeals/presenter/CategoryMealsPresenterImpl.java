package com.example.testtracker.main_app.categorymeals.presenter;

import android.util.Log;

import com.example.testtracker.main_app.categorymeals.model.CategoryMeals;
import com.example.testtracker.main_app.categorymeals.model.CategoryMealsReposetoryImpl;
import com.example.testtracker.main_app.categorymeals.view.CategoryMealsView;
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
    public void onCategoryMealsSuccess(List<CategoryMeals> meal) {
        view.showCatData(meal);
    }

    @Override
    public void onFailure(String message) {
        view.showError(message);
    }
}
