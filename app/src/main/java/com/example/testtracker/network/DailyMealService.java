package com.example.testtracker.network;


import com.example.testtracker.dailymeal.model.AllMeals;

import retrofit2.Call;
import retrofit2.http.GET;
public interface DailyMealService {
    @GET("random.php")
    Call<AllMeals> getAllMeals();
}

public interface AllCategoriesService {
    @GET("categories.php")
    Call<AllCategories> getAllCategories();
}


