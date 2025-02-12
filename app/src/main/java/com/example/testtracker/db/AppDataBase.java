package com.example.testtracker.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.testtracker.main_app.home.dailymeal.model.Meal;

@Database(entities = {Meal.class}, version = 1)

public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase appDataBase = null;
    public abstract MealDAO getproductDao();
    public static synchronized AppDataBase getInstance(Context context){
        if(appDataBase == null){
            appDataBase = Room.databaseBuilder(context,AppDataBase.class,"product_db").build();
        }
        return appDataBase;
    }
}