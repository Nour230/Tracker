package com.example.testtracker.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.utils.Converter;

@Database(entities = {SavedMeals.class}, version = 3)
@TypeConverters(Converter.class)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase appDataBase = null;
    public abstract MealDAO getMealsDao();
    public static synchronized AppDataBase getInstance(Context context){
        if(appDataBase == null){
            appDataBase = Room.databaseBuilder(context,AppDataBase.class,"meal_db").build();
        }
        return appDataBase;
    }
}