package com.example.testtracker.network;

import com.example.testtracker.models.dailymeal.AllMeals;
import com.example.testtracker.models.mealdetails.MealDetails;

import io.reactivex.rxjava3.core.Single;

public interface RemoteDataSource {

    default Single<AllMeals> getAllMeals(){
        return null;
    }
    default Single<MealDetails> getMealDetails(String id){
        return null;
    }
}
