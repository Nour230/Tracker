package com.example.testtracker.models.mealdetails;

import android.content.Context;

import com.example.testtracker.db.MealLocalDataSourceImpl;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.network.PlannerRemoteDataSource;
import com.example.testtracker.network.RemoteDataSource;

import io.reactivex.rxjava3.core.Completable;

public class MealDetailsRepositoryImpl {
    private PlannerRemoteDataSource remoteDataSource = null;
    private static MealDetailsRepositoryImpl repo = null;
    private MealLocalDataSourceImpl localDataSource =null;

    private MealDetailsRepositoryImpl(Context context){
        this.remoteDataSource = remoteDataSource.getInstance();
        this.localDataSource = localDataSource.getInstance(context);
    }
    public static MealDetailsRepositoryImpl getInstance(Context context){
        if(repo == null){
            repo = new MealDetailsRepositoryImpl(context);
        }
        return repo;}
    public Completable addMeal(SavedMeals meal) {
        return localDataSource.insertMeal(meal);
    }

}
