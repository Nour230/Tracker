package com.example.testtracker.models.dailymeal;

import android.content.Context;
import android.util.Log;

import com.example.testtracker.db.MealLocalDataSourceImpl;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.network.PlannerRemoteDataSource;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealRepositoryImpl {
    private  MealLocalDataSourceImpl localDataSource = null;
    private PlannerRemoteDataSource remoteDataSource = null;

    public MealRepositoryImpl(Context context) {
        this.localDataSource = localDataSource.getInstance(context);
        this.remoteDataSource = remoteDataSource.getInstance();
    }

    public static MealRepositoryImpl getInstance(Context context) {
        return new MealRepositoryImpl(context);
    }

    public  Single<List< MealDetails.MealsDTO>> getAllMeals() {
        return remoteDataSource.getAllMeals()
                .map(AllMeals::getMeals);
    }
    public Single<List<SavedMeals>> getAllSavedMeals() {
        return localDataSource.getFavMeals();
    }
    public Single<List<SavedMeals>> getAllPlanMeals() {
        return localDataSource.getPlanMeals();
    }

    public @NonNull Single<MealDetails.MealsDTO> getMealDetails(String mealId) {
        return remoteDataSource.getMealDetails(mealId)
                .map(mealDetails -> mealDetails.getMeals().get(0));
    }

    public Completable addMeal(SavedMeals meal) {
       return localDataSource.insertMeal(meal);
    }

    public Completable deleteMeal(SavedMeals meal) {
        return localDataSource.deleteMeal(meal);
    }
    public Completable clearLocalDatabase() {
        return localDataSource.clearLocalDatabase();
    }
    public void getDataFromFiteBase(){
        localDataSource.fetchDataFromFirebase();
    }
}