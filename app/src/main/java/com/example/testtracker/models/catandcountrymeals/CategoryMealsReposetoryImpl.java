package com.example.testtracker.models.catandcountrymeals;

import android.util.Log;

import com.example.testtracker.network.PlannerRemoteDataSource;
import com.example.testtracker.network.RemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class CategoryMealsReposetoryImpl implements RemoteDataSource {
    PlannerRemoteDataSource remoteDataSource;
    private static final String TAG = "MainActivity";

    private static CategoryMealsReposetoryImpl repo = null;
    private CategoryMealsReposetoryImpl(PlannerRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static CategoryMealsReposetoryImpl getInstance(PlannerRemoteDataSource remoteDataSource) {
        if (repo == null) {
            repo = new CategoryMealsReposetoryImpl(remoteDataSource);
        }
        return repo;}

    public Single<List<CategoryMeals>> getAllCategoriesMeals(String category) {
        Log.i(TAG, "getAllCategoriesMeals: "+category);
        return remoteDataSource.getMealsByCategory(category)
                .map(CategoryAllMeals::getMeals);
    }

    public Single<List<CategoryMeals>> getAllCountriesMeals(String country) {
        Log.i(TAG, "getAllCategoriesMeals: "+country);
        return remoteDataSource.getMealsByCountry(country)
                .map(CategoryAllMeals::getMeals);
    }
    public Single<List<CategoryMeals>> getAllIngrediantsMeals(String ingrediant) {
        Log.i(TAG, "getAllCategoriesMeals: "+ingrediant);
        return remoteDataSource.getMealsByIngrediant(ingrediant)
                .map(CategoryAllMeals::getMeals);
    }
}
