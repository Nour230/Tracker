package com.example.testtracker.db;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.testtracker.models.dailymeal.Meal;
import com.example.testtracker.models.db.SavedMeals;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealLocalDataSourceImpl {
    private MealDAO dao;
    private AppDataBase db;

    private static MealLocalDataSourceImpl clint = null;
    private Observable<List<SavedMeals>> storedMeals;

    private MealLocalDataSourceImpl(Context context){
         db = AppDataBase.getInstance(context);
        dao = db.getMealsDao();
        //storedMeals = dao.getFavMeals();
    }
    public static MealLocalDataSourceImpl getInstance(Context context){
        if(clint == null){
            clint = new MealLocalDataSourceImpl(context);
        }
        return clint;}




    public Single<List<SavedMeals>> getFavMeals() {
        return dao.getFavMeals();
    }


    public Completable insertMeal(SavedMeals meal) {
        Log.i("MainActivity", "insertMeal: "+meal.getIdMeal());
       return dao.insertMeal(meal);
    }

    public Completable deleteMeal(SavedMeals id) {
        return dao.deleteMeal(id);
    }
}
