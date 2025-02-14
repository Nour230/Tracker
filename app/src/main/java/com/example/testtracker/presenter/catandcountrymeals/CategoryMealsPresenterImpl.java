package com.example.testtracker.presenter.catandcountrymeals;

import android.util.Log;

import com.example.testtracker.models.catandcountrymeals.CategoryMeals;
import com.example.testtracker.models.catandcountrymeals.CategoryMealsReposetoryImpl;
import com.example.testtracker.presenter.intefaces.CategoryMealsPresenter;
import com.example.testtracker.view.interfaces.CategoryMealsView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryMealsPresenterImpl implements CategoryMealsPresenter {
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
        repo.getAllCategoriesMeals(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CategoryMeals>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onSuccess(@NonNull List<CategoryMeals> categoryMeals) {
                        view.showCatData(categoryMeals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError(e.getMessage());
                    }
                });
    }

    @Override
    public void getMealsByCountry(String country) {
        Log.d(TAG, "Fetching meals for country: " + country);
        repo.getAllCountriesMeals(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CategoryMeals>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<CategoryMeals> categoryMeals) {
                        view.showCatData(categoryMeals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError(e.getMessage());
                    }
                });
    }

    @Override
    public void getMealsByIngrediant(String ingrediant) {
        repo.getAllIngrediantsMeals(ingrediant)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CategoryMeals>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<CategoryMeals> categoryMeals) {
                        view.showCatData(categoryMeals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError(e.getMessage());
                    }
                });
    }

}
