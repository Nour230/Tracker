package com.example.testtracker.view.interfaces;

import com.example.testtracker.models.db.SavedMeals;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public interface PlanView {
    public void showError(String message);
    void showPlanMeals(List<SavedMeals> groupedMeals);
}
