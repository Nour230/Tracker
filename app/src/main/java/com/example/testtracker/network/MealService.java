package com.example.testtracker.network;


import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {

    @GET("random.php")
    Call<AllMeals> getAllMeals();

}
