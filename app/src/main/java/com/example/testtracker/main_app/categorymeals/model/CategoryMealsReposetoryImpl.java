package com.example.testtracker.main_app.categorymeals.model;

import com.example.testtracker.network.NetworkCallBack;
import com.example.testtracker.network.RemoteDataSource;

public class CategoryMealsReposetoryImpl implements RemoteDataSource {
    RemoteDataSource remoteDataSource;
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
        remoteDataSource.getMealsByCategory(category,networkCallBack);
    }

    @Override
    public void getMealsByCategory(String category, NetworkCallBack networkCallBack) {
        remoteDataSource.getMealsByCategory(category, networkCallBack);
    }
}
