package com.example.testtracker.main_app.allcategories.presenter;

import com.example.testtracker.main_app.allcategories.model.CategoriesRepositoryImpl;
import com.example.testtracker.main_app.allcategories.model.Category;
import com.example.testtracker.main_app.allcategories.view.CategoriesView;
import com.example.testtracker.main_app.allcountries.model.Country;
import com.example.testtracker.main_app.dailymeal.model.Meal;
import com.example.testtracker.network.NetworkCallBack;

import java.util.List;

public class CategoriesPresenterImpl implements CategoriesPresenter, NetworkCallBack {
    private final CategoriesView view;
    private final CategoriesRepositoryImpl repo;

    public CategoriesPresenterImpl(CategoriesView view, CategoriesRepositoryImpl repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getCategories() {
        repo.getAllCategories(this);
    }

    @Override
    public void onSuccess(List<Meal> products) {
    }

    @Override
    public void onCategoriesSuccess(List<Category> categories) {
        view.showCatData(categories);
    }

    @Override
    public void onCountrySuccess(List<Country> countries) {

    }

    @Override
    public void onFailure(String message) {
        view.showError(message);
    }
}
