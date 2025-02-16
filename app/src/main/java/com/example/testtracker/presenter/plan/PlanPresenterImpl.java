package com.example.testtracker.presenter.plan;

import android.util.Log;

import com.example.testtracker.models.dailymeal.MealRepositoryImpl;
import com.example.testtracker.presenter.intefaces.PlanPresenter;
import com.example.testtracker.view.interfaces.PlanView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImpl implements PlanPresenter {
    private final PlanView view;
    private final MealRepositoryImpl repo;
    public PlanPresenterImpl(PlanView view, MealRepositoryImpl repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void getPlanMeals() {
        repo.getAllPlanMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {view.showPlanMeals(meals);
                            Log.i("MainActivity", "getPlanMeals: "+meals.size());},
                        error -> view.showError(error.getMessage())
                );
    }
}
