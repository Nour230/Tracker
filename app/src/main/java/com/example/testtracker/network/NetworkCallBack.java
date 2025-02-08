package com.example.testtracker.network;

import com.example.testtracker.main_app.allcategories.model.Category;
import com.example.testtracker.main_app.dailymeal.model.Meal;

import java.util.List;

public interface NetworkCallBack {
    public void onSuccess(List<Meal> products);
    void onCategoriesSuccess(List<Category> categories); // Callback for categories

    public void onFailure(String message);
}
