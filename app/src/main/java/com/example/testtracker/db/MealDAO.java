package com.example.testtracker.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.testtracker.models.db.SavedMeals;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao

public interface MealDAO {
    @Query("SELECT * FROM meal Where isFav = 1")
    Single<List<SavedMeals>> getFavMeals();

    @Query("SELECT * FROM meal Where isPlan = 1")
    Single<List<SavedMeals>> getPlanMeals();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(SavedMeals meal);

    @Delete
    Completable deleteMeal(SavedMeals meal);

}
