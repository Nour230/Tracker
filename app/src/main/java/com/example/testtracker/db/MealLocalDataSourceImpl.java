package com.example.testtracker.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.testtracker.main_app.home.dailymeal.model.Meal;

import java.util.List;

public class MealLocalDataSourceImpl implements MealLocalDataSource{
    private MealDAO dao;
    private static MealLocalDataSourceImpl clint = null;
    private LiveData<List<Meal>> storedMeals;

    private MealLocalDataSourceImpl(Context context){
        AppDataBase db = AppDataBase.getInstance(context);
        dao = db.getproductDao();
        storedMeals = dao.getAllMeals();
    }
    public static MealLocalDataSourceImpl getInstance(Context context){
        if(clint == null){
            clint = new MealLocalDataSourceImpl(context);
        }
        return clint;}

    @Override
    public LiveData<List<Meal>> getAllMeals() {
        return storedMeals;
    }

    @Override
    public void insertMeal(Meal meal) {
        new Thread(()->{
            dao.insertMeal(meal);
        }).start();
    }

    @Override
    public void deleteMeal(Meal meal) {
        new Thread(()->{
            dao.deleteMeal(meal);
        }).start();
    }
}
