package com.example.testtracker.main_app.home.allcategories.presenter;

import com.example.testtracker.main_app.home.allcategories.model.CategoriesRepositoryImpl;
import com.example.testtracker.main_app.home.allcategories.model.Category;
import com.example.testtracker.main_app.home.allcategories.view.CategoriesView;
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
    public void onCategoriesSuccess(List<Category> categories) {
        view.showCatData(categories);
    }

    @Override
    public void onFailure(String message) {
        view.showError(message);
    }
}
