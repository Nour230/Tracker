package com.example.testtracker.presenter.mealdetails;

import android.util.Log;

import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.models.mealdetails.MealDetailsRepositoryImpl;
import com.example.testtracker.presenter.intefaces.MealDetailsPresenter;
import com.example.testtracker.view.interfaces.MealDetailsView;
import com.example.testtracker.network.NetworkCallBack;

import java.util.List;

public class MealDetailsPresenterImpl implements MealDetailsPresenter, NetworkCallBack {
    private static final String TAG = "MainActivity";
    private final MealDetailsView view;
private final MealDetailsRepositoryImpl repo;

    public MealDetailsPresenterImpl(MealDetailsView view, MealDetailsRepositoryImpl repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void onMealSussecc(List<MealDetails.MealsDTO> meals) {
        if (meals != null && !meals.isEmpty()) {
            Log.i(TAG, "onMealSussecc: "+meals.get(0).getStrMeal());
            MealDetails.MealsDTO meal = meals.get(0);
            view.showMealDetails(meal);
        } else {
            view.showError("No meal details found.");
        }
    }    @Override
    public void onFailure(String message) {
        view.showError(message);
    }

    @Override
    public void getMealDetails(String mealId) {
        repo.getMealDetails(mealId,this);
    }
}
