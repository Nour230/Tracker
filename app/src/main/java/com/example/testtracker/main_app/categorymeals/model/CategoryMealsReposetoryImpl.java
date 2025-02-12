package com.example.testtracker.main_app.categorymeals.model;

import android.util.Log;

import com.example.testtracker.network.NetworkCallBack;
import com.example.testtracker.network.RemoteDataSource;

public class CategoryMealsReposetoryImpl implements RemoteDataSource {
    RemoteDataSource remoteDataSource;
    private static final String TAG = "MainActivity";

    private static CategoryMealsReposetoryImpl repo = null;
    private CategoryMealsReposetoryImpl(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static CategoryMealsReposetoryImpl getInstance(RemoteDataSource remoteDataSource) {
        if (repo == null) {
            repo = new CategoryMealsReposetoryImpl(remoteDataSource);
        }
        return repo;}



    public void getAllCategoriesMeals(String category,NetworkCallBack networkCallBack) {
        Log.i(TAG, "getAllCategoriesMeals: "+category);
        remoteDataSource.getMealsByCategory(category,networkCallBack);
    }

    public void getAllCountriesMeals(String country,NetworkCallBack networkCallBack) {
        Log.i(TAG, "getAllCategoriesMeals: "+country);
        remoteDataSource.getMealsByCountry(country,networkCallBack);
    }


    @Override
    public void getMealsByCategory(String category, NetworkCallBack networkCallBack) {
        remoteDataSource.getMealsByCategory(category, networkCallBack);
    }

    @Override
    public void getMealsByCountry(String country, NetworkCallBack networkCallBack) {
        remoteDataSource.getMealsByCountry(country, networkCallBack);
    }
}
