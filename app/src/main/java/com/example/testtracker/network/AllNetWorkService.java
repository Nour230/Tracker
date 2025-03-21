package com.example.testtracker.network;


import com.example.testtracker.main_app.categorymeals.model.CategoryAllMeals;
import com.example.testtracker.main_app.home.allcategories.model.AllCategories;
import com.example.testtracker.main_app.home.allcountries.model.AllCounties;
import com.example.testtracker.main_app.home.dailymeal.model.AllMeals;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AllNetWorkService {
    @GET("random.php")
    Call<AllMeals> getAllMeals();
     @GET("categories.php")
     Call<AllCategories> getAllCategories();

     @GET("list.php?a=list")
     Call<AllCounties> getAllCountries();

     @GET("filter.php")
     Call<CategoryAllMeals> getMealsByCategory(@Query("c") String category);
}




