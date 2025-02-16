package com.example.testtracker.presenter.mealdetails;

import android.util.Log;

import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.models.mealdetails.MealDetailsRepositoryImpl;
import com.example.testtracker.view.interfaces.MealDetailsView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImpl  {
    private static final String TAG = "MainActivity";
    private final MealDetailsView view;
private final MealDetailsRepositoryImpl repo;

    public MealDetailsPresenterImpl(MealDetailsView view, MealDetailsRepositoryImpl repo) {
        this.view = view;
        this.repo = repo;
    }

    public void addToFav(SavedMeals meal) {
        repo.addMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.addToFav();
                        },
                        error -> {
                            view.showError(error.getMessage());}
                );

    }
    public void addToPlan(SavedMeals meal){
        repo.addMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.addToPlan();
                        },
                        error -> {
                            view.showError(error.getMessage());}
                );
    }
    }
