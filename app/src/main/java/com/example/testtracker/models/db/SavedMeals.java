package com.example.testtracker.models.db;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.utils.Converter;

import java.util.Date;

@Entity(tableName = "meal",
primaryKeys = {"idMeal","userId","date"})
@TypeConverters(Converter.class)
public class SavedMeals {

    @NonNull
    String idMeal;

    @NonNull
    String userId;

    @NonNull
    String date;
    @TypeConverters(Converter.class)
    MealDetails.MealsDTO meal;

    boolean isFav;
    boolean isPlan;

    public SavedMeals(@NonNull String idMeal, @NonNull String userId, @NonNull String date, MealDetails.MealsDTO meal, boolean isFav, boolean isPlan) {
        this.idMeal = idMeal;
        this.userId = userId;
        this.date = date;
        this.meal = meal;
        this.isFav = isFav;
        this.isPlan = isPlan;
    }


    @NonNull
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public MealDetails.MealsDTO getMeal() {
        return meal;
    }

    public void setMeal(MealDetails.MealsDTO meal) {
        this.meal = meal;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public boolean isPlan() {
        return isPlan;
    }

    public void setPlan(boolean plan) {
        isPlan = plan;
    }
}
