package com.example.testtracker.main_app.dailymeal.presenter;

import com.example.testtracker.main_app.allcategories.model.Category;
import com.example.testtracker.main_app.dailymeal.view.DailyMealView;
import com.example.testtracker.main_app.dailymeal.model.Meal;
import com.example.testtracker.main_app.dailymeal.model.MealRepositoryImpl;
import com.example.testtracker.network.NetworkCallBack;

import java.util.List;

public class DailyMealPresenterImpl implements DailyMealPresenter, NetworkCallBack {
    private final DailyMealView view;
    private final MealRepositoryImpl repo;

    public DailyMealPresenterImpl(DailyMealView view, MealRepositoryImpl repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getProducts() {
        repo.getAllMeals(this);
    }

    @Override
    public void addToFav(Meal meal) {
        repo.addMeal(meal);
    }

    @Override
    public void addToPlan(Meal meal) {
        repo.addMeal(meal);
    }

    @Override
    public void onSuccess(List<Meal> meal) {
        view.showData(meal);
    }

    @Override
    public void onCategoriesSuccess(List<Category> categories) {

    }

    @Override
    public void onFailure(String message) {
        view.showError(message);
    }
}
