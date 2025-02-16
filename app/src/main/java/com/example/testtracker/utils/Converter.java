package com.example.testtracker.utils;

import androidx.room.TypeConverter;

import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

public class Converter {
    private static final Gson gson = new Gson();
//    @TypeConverter
//    public static Long fromDate(Date date) {
//        return date == null ? null : date.getTime();
//    }
//
//    @TypeConverter
//    public static Date toDate(Long timestamp) {
//        return timestamp == null ? null : new Date(timestamp);
//    }

    @TypeConverter
    public static String fromMealsDTO(MealDetails.MealsDTO meal) {
        if (meal == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(meal);
    }

    // Convert JSON String back to MealDetails.MealsDTO
    @TypeConverter
    public static MealDetails.MealsDTO toMealsDTO(String json) {
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<MealDetails.MealsDTO>() {}.getType();
        return gson.fromJson(json, type);
    }
}
