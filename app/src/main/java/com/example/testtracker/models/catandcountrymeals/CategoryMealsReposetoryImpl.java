package com.example.testtracker.models.catandcountrymeals;

import android.content.Context;
import android.util.Log;

import com.example.testtracker.db.MealLocalDataSourceImpl;
import com.example.testtracker.models.dailymeal.AllMeals;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.network.PlannerRemoteDataSource;
import com.example.testtracker.network.RemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class CategoryMealsReposetoryImpl implements RemoteDataSource {
    PlannerRemoteDataSource remoteDataSource = null;
    private static final String TAG = "MainActivity";
    private MealLocalDataSourceImpl localDataSource =null;


    private static CategoryMealsReposetoryImpl repo = null;
    private CategoryMealsReposetoryImpl(Context context) {
        this.remoteDataSource = remoteDataSource.getInstance();
        this.localDataSource = localDataSource.getInstance(context);
    }

    public static CategoryMealsReposetoryImpl getInstance(Context context) {
        if (repo == null) {
            repo = new CategoryMealsReposetoryImpl(context);
        }
        return repo;}

    public Single<List<MealDetails.MealsDTO>> getAllCategoriesMeals(String category) {
        Log.i(TAG, "getAllCategoriesMeals: " + category);
        return remoteDataSource.getMealsByCategory(category)
                .map(categoryAllMeals -> categoryAllMeals.getMeals()); // Correct usage of method reference
    }
    public Single<List<MealDetails.MealsDTO>> getAllCountriesMeals(String country) {
        Log.i(TAG, "getAllCountriesMeals: " + country);
        return remoteDataSource.getMealsByCountry(country)
                .map(categoryAllMeals -> categoryAllMeals.getMeals()); // Use lambda instead of method reference
    }


    public Single<List<MealDetails.MealsDTO>> getAllIngrediantsMeals(String ingrediant) {
        Log.i(TAG, "getAllIngrediantsMeals: " + ingrediant);
        return remoteDataSource.getMealsByIngrediant(ingrediant)
                .map(categoryAllMeals -> categoryAllMeals.getMeals()); // Use lambda instead of method reference
    }
}
