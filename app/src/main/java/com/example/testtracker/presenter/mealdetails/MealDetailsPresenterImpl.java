package com.example.testtracker.presenter.mealdetails;

import android.util.Log;

import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.models.mealdetails.MealDetailsRepositoryImpl;
import com.example.testtracker.presenter.intefaces.MealDetailsPresenter;
import com.example.testtracker.view.interfaces.MealDetailsView;

import java.util.List;

public class MealDetailsPresenterImpl  {
    private static final String TAG = "MainActivity";
    private final MealDetailsView view;
private final MealDetailsRepositoryImpl repo;

    public MealDetailsPresenterImpl(MealDetailsView view, MealDetailsRepositoryImpl repo) {
        this.view = view;
        this.repo = repo;
    }

    }
