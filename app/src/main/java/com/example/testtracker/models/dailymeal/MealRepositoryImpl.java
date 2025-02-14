package com.example.testtracker.models.dailymeal;

import com.example.testtracker.db.MealLocalDataSource;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.network.PlannerRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealRepositoryImpl {
    private final MealLocalDataSource localDataSource;
    private final PlannerRemoteDataSource remoteDataSource;

    public MealRepositoryImpl(MealLocalDataSource localDataSource, PlannerRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static MealRepositoryImpl getInstance(MealLocalDataSource localDataSource, PlannerRemoteDataSource remoteDataSource) {
        return new MealRepositoryImpl(localDataSource, remoteDataSource);
    }

    public  Single<List<Meal>> getAllMeals() {
        return remoteDataSource.getAllMeals()
                .map(AllMeals::getMeals);
    }

    public @NonNull Single<MealDetails.MealsDTO> getMealDetails(String mealId) {
        return remoteDataSource.getMealDetails(mealId)
                .map(mealDetails -> mealDetails.getMeals().get(0));
    }

    public void addMeal(Meal meal) {
        localDataSource.insertMeal(meal);
    }
}